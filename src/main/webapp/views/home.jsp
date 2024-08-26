<%@include file="/common/taglib.jsp" %>
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html lang="en">

    <head>
      <!-- Required meta tags -->
      <meta charset="UTF-8">
      <title>Meme World</title>
      <%-- <link rel="stylesheet" href="<c:url value='/template/web/css/bootstrap.min.css'/>">--%>
        <%-- <link rel="stylesheet" href="<c:url value='/template/web/css/customize.css'/>">--%>
          <%-- <link rel="stylesheet" href="<c:url value='/template/web/css/dropzone.css'/>">--%>
            <%-- <link rel="stylesheet" href="<c:url value='/template/web/css/glightbox.min.css'/>">--%>
              <%-- <script src="<c:url value='/template/web/js/kit.42d5adcbca.js' />"></script>--%>

                <!-- Dark mode -->
                <%-- <script src="<c:url value='/template/web/js/theme.js' />"></script>--%>
    </head>

    <body>
      <!-- **************** MAIN CONTENT START **************** -->
      <main class="container">
        <div class="row py-2">
          <div class="col-md-3 py-1">
            <div class="d-grid gap-2 px-2 pb-2">
              <a href="<c:url value='/create-post'/>" role="button" class="btn btn-sm btn-info">
                + viết bài
              </a>
            </div>
            <div class="card">
              <div class="card-header border-0 pb-0">
                <h5 class="card-title">Chú ý</h5>
                <!-- Button modal -->
              </div>
              <!-- Card body START -->
              <div class="card-body position-relative pt-0">
                <p>Đăng bài đi. Rồi chờ kiểm duyệt họ duyệt cho lên trang chủ. Rán đợi đi nha heehee.</p>
              </div>
              <!-- Card body END -->
            </div>
          </div>

          <div class="col-md-9">


            <c:forEach var="post" items="${posts}">
              <article class="blog-post py-1">
                <div class="card">
                  <div class="row">
                    <div class="row">
                      <div class="d-flex justify-content-between p-2 px-3">
                        <div class="d-flex flex-row align-items-center">
                          <img src="${post.created.thumbnail}" width="40" class="rounded-circle">
                          <div class="d-flex flex-column ml-2 px-2">
                            <span class="fw-semibold"><a href="/p?id="> ${post.created.name} </a></span>
                            <small class="timestamp mr-2">${post.createdDate}</small>
                          </div>

                        </div>
                        <div class="dropdown float-lg-end" style="margin-right:-15px">
                          <a class="text-secondary btn btn-secondary-soft-hover py-1 px-2" id="dropdownTable2"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fa fa-ellipsis-v text-secondary" aria-hidden="true"></i>
                          </a>
                          <ul class="dropdown-menu px-2 py-3 ms-sm-n4 ms-n5" aria-labelledby="dropdownTable">
                            <li><a class="dropdown-item border-radius-md" href="#">Ẩn</a></li>
                            <li><a class="dropdown-item border-radius-md" href="javascript:;">Báo cáo</a></li>
                            <li><a class="dropdown-item border-radius-md" href="javascript:;">Bật thông báo</a></li>
                          </ul>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6">

                      <div class="p-2">
                        <h6 class="text-justify"><a href="/w?v="> ${post.title} </a></h6>
                      </div>


                      <c:if test="${not empty post.imagePath}">
                        <div class="card">
                          <img src="<c:url value = '/post-image-api?path=${post.imagePath}'/>" class="card-img">
                        </div>
                      </c:if>

                      <div class="p-2 content-container">
                        <p class="text-justify content">${post.content}</p>
                      </div>
                    </div>

                    <div class="col-md-6">
                      <div class="me-sm-1">
                        <div class="d-flex justify-content-between align-items-center bg-dark rounded">
                          <div class="">
                            <div class="d-flex flex-row d-flex align-items-center ">
                              <div class="cont">
                                <div class="stars py-1" data-vote-value="${post.currentVote}">
                                  <form action="">

                                    <input class="star star-5 star-none-wrap" id="star-5-${post.id}" type="radio"
                                      name="star" />
                                    <label class="star star-5 star-none-wrap" for="star-5-${post.id}"></label>

                                    <input class="star star-4 star-none-wrap" id="star-4-${post.id}" type="radio"
                                      name="star" />
                                    <label class="star star-4 star-none-wrap" for="star-4-${post.id}"></label>
                                    <input class="star star-3 star-none-wrap" id="star-3-${post.id}" type="radio"
                                      name="star" />
                                    <label class="star star-3 star-none-wrap" for="star-3-${post.id}"></label>
                                    <input class="star star-2 star-none-wrap" id="star-2-${post.id}" type="radio"
                                      name="star" />
                                    <label class="star star-2 star-none-wrap" for="star-2-${post.id}"></label>
                                    <input class="star star-1 star-none-wrap fist-star" id="star-1-${post.id}"
                                      type="radio" name="star" />
                                    <label class="star star-1 star-none-wrap fist-star" for="star-1-${post.id}"></label>
                                  </form>
                                </div>
                              </div>
                            </div>
                          </div>

                          <div class="d-flex flex-row muted-color px-2"> <span class="ml-2 text-nowrap text-white"> <i
                                class="fa fa-star" aria-hidden="true"></i>${post.avgVote}/<c:choose>
                                <c:when test="${post.voteCount == 0}">0</c:when>
                                <c:otherwise>5</c:otherwise>
                              </c:choose>
                            </span>
                          </div>
                        </div>
                        <hr>

                        <div class="justify-content-between rounded">
                          <c:if test="${not empty post.tab}">
                            <div class="tags">
                              <c:forEach items="${post.tab}" var="tag">
  <%--                            <span class="badge bg-primary ">#tag1</span>--%>
  <%--                            <span class="badge bg-secondary  ">#tag2</span>--%>
                                  <span class="badge bg-success ">#${tag}</span>
                              </c:forEach>
                            </div>
                          </c:if>
                        </div>


                        <hr>

                        <c:if test="${not empty post.source}">
                          <div class="d-flex justify-content-between align-items-center">
                            <div class="d-flex flex-row icons d-flex align-items-center">
                              <i class="fa fa-duotone fa-link" aria-hidden="true"></i>Nguồn: <a href="${post.source}">
                                <c:if test="${not empty post.sourceName}"> ${post.sourceName}</c:if>
                                <c:if test="${empty post.sourceName}">${post.source}</c:if>
                              </a>
                            </div>

                            <div class="d-flex flex-row muted-color"> <span class="ml-2"> <i
                                  class="fa fa-envelope-open-o" aria-hidden="true"></i></span>
                            </div>
                          </div>
                          <hr>
                        </c:if>


                        <!-- Comments -->
                        <c:if test="${empty post.comments}">
                          <div class="card mb-2">
                            <h2 class="text-center text-muted p-3"> Không có bình luận </h2>
                          </div>
                        </c:if>
                        <c:if test="${not empty post.comments}">

                          <ul class="comment-wrap list-unstyled">
                            <!-- Comment item START
                              <c:forEach var="comment" items="${post.comments}">

                              <!-- Comment item START -->
                            <li class="comment-item">
                              <div class="d-flex">
                                <!-- Avatar -->
                                <div class="avatar avatar-xs ms-1">
                                  <a href="#!"><img class="avatar-img rounded-circle" src="${comment.created.thumbnail}"
                                      alt=""></a>
                                </div>
                                <!-- Comment by -->
                                <div class="ms-2">
                                  <div class="bg-light p-1 rounded">
                                    <div class="d-flex justify-content-between">
                                      <div class="mb-1 fw-semibold"> <a href="/p?id="> ${comment.created.name} </a> </div>
                                      <small class="ms-2 timestamp">${comment.createdDate}</small>
                                    </div>
                                    <div class="content-comment-alt">
                                      <p class="small mb-0 content-comment">${comment.content}</p>
                                    </div>
                                  </div>
                                  <!-- Comment react -->
                                  <ul class="nav nav-divider pt-2 small">
                                    <li class="nav-item">
                                      <a class="nav-link" href="#!"> <i class="fa fa-star" aria-hidden="true"></i>
                                        <c:choose>
                                          <c:when test="${comment.voteCount != 0}">
                                            ${comment.voteCount}
                                          </c:when>
                                        </c:choose>

                                      </a>
                                    </li>
                                    <li class="nav-item">
                                      <a class="nav-link" href="#!"> Trả lời</a>
                                    </li>
                                    <li class="nav-item">
                                      <a class="nav-link" href="#!"> Xem 6 câu trả lời</a>
                                    </li>
                                  </ul>
                                </div>
                              </div>
                            </li>
                            <!-- Comment item END -->

            </c:forEach>

            </ul>

            </c:if>


            <c:if test="${not empty cookie.token}">
              <!-- Reply -->
              <div class="d-flex mb-3">
                <!-- Avatar -->
                <div class="avatar avatar-xs-4 me-2">

                  <a href="#!">
                    <c:if test="${not empty userModel.avatar}">
                      <img src="${userModel.avatar}" alt="mdo" width="32" height="32" class="rounded-circle">
                    </c:if>
                  </a>
                </div>
                <!-- Comment box  -->
                <form class="nav nav-item w-100 position-relative">
                  <textarea data-autoresize="" class="form-control pe-5 bg-light" rows="1"
                    placeholder="Add a comment..."></textarea>
                  <button
                    class="nav-link bg-transparent px-3 position-absolute top-50 end-0 translate-middle-y border-0"
                    type="submit">
                    <i class="fa fa-paper-plane-o" aria-hidden="true"></i>
                  </button>
                </form>
              </div>
            </c:if>

            <c:if test="${empty cookie.token}">
              <div class="p-2 text-center">
                <a href="/login" role="button" class="btn btn-sm btn-info">Đăng nhập để bình luận</a>
              </div>
            </c:if>


          </div>
        </div>
        </div>
        </div>
        </article>

        </c:forEach>
        <c:if test="${empty posts}">
          <article class="blog-post py-1">
            <div class="card">
              <h2 class="text-center text-muted p-5"> Trống </h2>
            </div>
          </article>
        </c:if>
        <c:if test="${not empty posts}">
          <div class="d-grid gap-2 pt-2">
            <a href="#!" role="button" class="btn btn-sm btn-loader btn-info" data-bs-toggle="button"
              aria-pressed="true">
              <span class="load-text"> Xem thêm... </span>
              <div class="load-icon">
                <div class="spinner-grow spinner-grow-sm" role="status">
                  <span class="visually-hidden">Đang tải...</span>
                </div>
              </div>
            </a>
          </div>
        </c:if>

        </div>

        </div>
      </main>
      <script src="<c:url value='/public/web/js/token/refreshToken.js'/> "></script>
      <script src="<c:url value='/public/web/js/bootstrap.bundle.min.js'/>"></script>
      <script src="<c:url value='/public/web/js/customize.js'/> "></script>
      <script src="<c:url value='/public/web/js/loginDirection/loginDirection.js'/> "></script>
    <script src="<c:url value='/public/web/js/jquery-3.5.1.js'/> " type="text/javascript"></script>
    <script>
    $(document).ready(function() {
        $('input[name="star"]').on('click', function() {
            var starValue = $(this).attr('id').split('-')[1];
            var postId = $(this).attr('id').split('-')[2];
            $.ajax({
                        url: '/vote-post',
                        method: 'POST',
                        data: {
                            _post: postId,
                            _vote: starValue
                        },
                        success: function(response) {
                            console.log('>>success:',response);
                        },
                        error: function(xhr, status, error) {
                            console.log('>>error:',error);
                        }
                    });
        });
    });
    </script>


    </body>

    </html>