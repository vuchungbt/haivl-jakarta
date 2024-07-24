package net.blwsmartware.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.blwsmartware.constant.IConstant;
import net.blwsmartware.model.UserModel;
import net.blwsmartware.service.IUserService;
import net.blwsmartware.util.JWTUtil;
import net.blwsmartware.util.RouterUtil;

import java.io.IOException;

@WebFilter("/loginFilter")
public class AuthorizationFilter implements Filter {

    @Inject
    private IUserService userService;
    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURI();
        handleUri(url, request);
        Cookie tokenCookie = getCookieToken(request);
        if (tokenCookie != null && !tokenCookie.getValue().isEmpty()) {

            try {
                String token = tokenCookie.getValue();
                handleValidToken(token, url, request, response, filterChain);

            } catch (TokenExpiredException e) {
                handleExpiredToken(url, request, response, filterChain);

            } catch (Exception e) {
                System.out.println("Invalid token: " + e.getMessage());
                handleInvalidToken(request, response);
            }
        } else {
            handleNoToken(url, request, response, filterChain);
        }
    }

    private UserModel getInfoFromToken(String token) {
        Long id = JWTUtil.getIdUserFromToken(token);
        return userService.findByID(id);
    }

    private void handleUri(String url, HttpServletRequest request) {
        String link = RouterUtil.getRouter(1, request);
        request.setAttribute("router", link);
    }

    private void handleValidToken(String token, String url, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        UserModel userModel = getInfoFromToken(token);
        request.setAttribute("status", 200);
        request.setAttribute("userModel", userModel);
        if (url.startsWith("/login")) {
            response.sendRedirect(request.getContextPath() + "/");
        } else if (url.startsWith("/admin")) {
            Long idAdmin = userService.getRoleIDByRoleCode(IConstant.ADMIN);
            if (userModel.getRoleId().equals(idAdmin)) {
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/login?message=not_permission&alert=danger");
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

    private void handleExpiredToken(String url, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        if (checkURLForNoToken(url)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login?message=token_expired&alert=danger");
        }
    }

    private void handleInvalidToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect(request.getContextPath() + "/login?message=token_invalid&alert=danger");
    }

    private void handleNoToken(String url, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (checkURLForNoToken(url)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login?message=not_permission&alert=danger");
        }
    }

    private boolean checkURLForNoToken(String url) {
        return !url.startsWith("/admin");
    }

    private Cookie getCookieToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token"))
                    return cookie;
            }
        }
        return null;
    }

    @Override
    public void destroy() {

    }
}
