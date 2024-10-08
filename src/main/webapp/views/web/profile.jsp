<%@include file="/common/taglib.jsp" %>
  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <!DOCTYPE html>
    <html>

    <head>
      <!-- Required meta tags -->
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Profile</title>

    </head>

    <body>
      <main class="container">
        <div class="row py-2">

          <!-- Container START -->
          <div class="container">
            <div class="row">
              <div class="col-lg-8">

                <div class="card">
                  <div class="h-200px rounded-top"
                    style="background-image:url(https://i.imgur.com/xhzhaGA.jpg); background-position: center; background-size: cover; background-repeat: no-repeat;">
                  </div>
                  <!-- Card body START -->
                  <div class="card-body py-0">
                    <div class="d-sm-flex align-items-start text-center text-sm-start">
                      <div>
                        <!-- Avatar -->
                        <div class="avatar avatar-xxl mt-n5 mb-3">
                          <c:if test="${not empty userModel.avatar}">
                            <img class="avatar-img rounded-circle border border-white border-3"
                                 src="${userModel.avatar}" alt="">
                            <c:if test="${empty userModel.avatar}">
                              <img class="avatar-img rounded-circle border border-white border-3"
                                   src='<c:url value = "/template/web/img/avatar/user.png"/>' alt="">
                            </c:if>
                          </c:if>

                        </div>


                      </div>
                      <div class="ms-sm-4 mt-sm-3">
                        <!-- Info -->
                        <h1 class="mb-0 h5">${userModel.name} <i class="bi bi-patch-check-fill text-success small"></i></h1>
                        <p>${count} bài đăng</p>
                      </div>
                      <!-- Button -->
                      <div class="d-flex mt-3 justify-content-center ms-sm-auto">
                        <button class="btn btn-danger-soft me-2" type="button"> <i class="bi bi-pencil-fill pe-1"></i>
                          Sửa ảnh nền </button>
                        <div class="dropdown">
                          <!-- Card share action menu -->
                          <button class="icon-md btn btn-light" type="button" id="profileAction2"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fa fa-ellipsis-v text-secondary" aria-hidden="true"></i>
                          </button>
                          <!-- Card share action dropdown menu -->
                          <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="profileAction2">
                            <li><a class="dropdown-item" href="#"> <i class="bi bi-bookmark fa-fw pe-2"></i>Share
                                profile in a
                                message</a></li>

                            <li><a class="dropdown-item" href="#"> <i class="bi bi-gear fa-fw pe-2"></i>Profile
                                settings</a>
                            </li>
                          </ul>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>



                <div class="card">
                  <div class="card-body py-2 border-0 d-flex align-items-center justify-content-between">
                    <h1 class="h5 mx-2 mt-1">Bài viết</h1>
                    <!-- Notification action START -->
                    <div class="float-lg-end">
                      <select id = "select-post-status" class="form-select form-select-sm " aria-label="Small select example">
                        <option selected>All</option>
                        <option value="0">Pending</option>
                        <option value="1">Published</option>
                        <option value="2">Archie</option>
                      </select>

                    </div>

                  </div>
                </div>


                <div class="blog-post py-1">
                  <c:forEach var="post" items="${posts}">
                    <div class="card mt-2">

                      <div class="row">
                        <div class="d-flex justify-content-between p-2 px-3">
                          <div class="d-flex flex-row align-items-center">
                            <div class="d-flex flex-column ml-2 px-2">
                              <h5 class="m-1">
                                <a href="/w?v="> ${post.title} </a>
                              </h5>
                            </div>

                          </div>
                          <div class="dropdown float-lg-end">
                            <a class="text-secondary btn btn-secondary-soft-hover py-1 px-2" id="dropdownTable2"
                              data-bs-toggle="dropdown" aria-expanded="false">
                              <i class="fa fa-ellipsis-h text-secondary" aria-hidden="true"></i>
                            </a>
                            <ul class="dropdown-menu px-2 py-3 ms-sm-n4 ms-n5" aria-labelledby="dropdownTable">
                              <li>
                                <form action="<c:url value ='/delete-post'/>" method="post">
                                  <button style="background: none; border: none; padding: 0; margin: 0; text-align: left; font: inherit; color: inherit;"
                                        type = submit>
                                    <a class="dropdown-item border-radius-md" >Xóa</a>
                                  </button>
                                  <input type="hidden" value="${post.id}" name = "id">
                                </form>
                              </li>
                              <c:url var="editPost" value="/edit-post">
                                <c:param name="id" value="${post.id}" />
                              </c:url>
                              <li><a class="dropdown-item border-radius-md" href='${editPost}'>Chỉnh sửa</a></li>
                              <li><a class="dropdown-item border-radius-md" href="javascript:;">Bật/Ẩn thông báo</a>
                              </li>
                            </ul>
                          </div>
                          </div>
                      </div>
                      <div class="row">


                        <div class="col-md-4 pe-0">
                          <c:if test="${not empty post.imagePath}">
                            <div class="card m-2 ">
                                <img src="<c:url value='/post-image-api?path=${post.imagePath}'/>" class="card-img">
                            </div>
                          </c:if>
                        </div>

                        <div class="col-md-8 p-0">
                          <div class="p-2 content-container mb-4">
                            <p class="text-justify content">${post.content}</p>
                          </div>
                          <ul class="nav nav-stack small pb-2" style="position: absolute; bottom: 0;">
                            <li class="nav-item timestamp">
                              <i class="bi bi-calendar-check pe-1"></i>
                              <c:if test="${empty post.modifiedDate}">${post.createdDate}</c:if>
                              <c:if test="${not empty post.modifiedDate}">${post.modifiedDate}</c:if>
                            </li>
                            <li class="nav-item">
                              <i class="bi bi-geo-alt pe-1"></i> Trạng thái: ${post.statusName}
                            </li>
                            <li class="nav-item">
                              <i class="bi bi-people pe-1"></i> Vote: ${post.avgVote}/5 (${post.voteCount})
                            </li>
                            <li class="nav-item">
                              <i class="bi bi-people pe-1"></i> Comment: ${post.comments.size()}
                            </li>
                          </ul>
                        </div>


                      </div>
                    </div>
                  </c:forEach>

                </div>
              </div>

              <!-- Right sidebar START -->
              <div class="col-lg-4">

                <div class="row g-4">

                  <!-- Card START -->
                  <div class="col-sm-6 col-lg-12">
                    <div class="card">
                      <div class="card-header d-sm-flex justify-content-between align-items-center border-0">
                        <h5 class="card-title">Thông tin
                        </h5>
                        <a id = "profileEdit" class="btn btn-primary-soft btn-sm" data-bs-toggle="modal" data-bs-target="#exampleModal">
                          Chỉnh sửa</a>

                      </div>
                      <!-- Card body START -->
                      <div class="card-body position-relative pt-0">
                        <c:if test="${not empty userModel.description}">
<%--                          <p>He moonlights difficult engrossed it, sportsmen. Interested has all Devonshire difficulty gay--%>
<%--                            assistance joy.</p>--%>
                          <p>${userModel.description}</p>
                        </c:if>
                        <!-- Date time -->
                        <ul class="list-unstyled mt-3 mb-0">
<%--                          <li class="mb-2"> <i class="bi bi-calendar-date fa-fw pe-1"></i> Born: <strong> October 20,--%>
<%--                              1990--%>
<%--                            </strong> </li>--%>
<%--                          <li class="mb-2"> <i class="bi bi-heart fa-fw pe-1"></i> Status: <strong> Single </strong>--%>
<%--                          </li>--%>
                          <c:if test="${not empty userModel.tel}">
                            <li> <i class="bi bi-envelope fa-fw pe-1"></i> Phone: <strong> ${userModel.tel} </strong>
                            </li>
                          </c:if>
                          <c:if test="${not empty userModel.email}">
                            <li> <i class="bi bi-envelope fa-fw pe-1"></i> Email: <strong> ${userModel.email} </strong>
                            </li>
                          </c:if>
                        </ul>
                      </div>
                      <!-- Card body END -->
                    </div>
                  </div>
                  <!-- Card END -->

                  <!-- Card START -->
                  <div class="col-sm-6 col-lg-12">
                    <div class="card">
                      <!-- Card header START -->
                      <div class="card-header d-sm-flex justify-content-between align-items-center border-0">
                        <h5 class="card-title">Thống kê <span class="badge bg-success bg-opacity-10 text-info">230
                            điểm</span>
                        </h5>
                        <a class="btn btn-primary-soft btn-sm" href="#!"> Xem thêm</a>
                      </div>
                      <!-- Card header END -->
                      <!-- Card body START -->
                      <div class="card-body position-relative pt-0">
                        <div class="row g-3">

                          <div class="col-6">
                            <!-- Friends item START -->
                            <div class="card shadow-none text-center h-100">
                              <!-- Card body -->
                              <div class="card-body p-2 pb-0">
                                <div class="avatar avatar-story avatar-xl">
                                  <a href="#!"><img class="avatar-img rounded-circle" src="img/avatar/02.jpg"
                                      alt=""></a>
                                </div>
                                <h6 class="card-title mb-1 mt-3"> <a href="#!"> Amanda Reed </a></h6>
                                <p class="mb-0 small lh-sm">16 mutual connections</p>
                              </div>
                              <!-- Card footer -->
                              <div class="card-footer p-2 border-0">
                                <button class="btn btn-sm btn-primary" data-bs-toggle="tooltip" data-bs-placement="top"
                                  title="Send message"> <i class="bi bi-chat-left-text"></i> </button>
                                <button class="btn btn-sm btn-danger" data-bs-toggle="tooltip" data-bs-placement="top"
                                  title="Remove friend"> <i class="bi bi-person-x"></i> </button>
                              </div>
                            </div>
                            <!-- Friends item END -->
                          </div>

                          <div class="col-6">
                            <!-- Friends item START -->
                            <div class="card shadow-none text-center h-100">
                              <!-- Card body -->
                              <div class="card-body p-2 pb-0">
                                <div class="avatar avatar-xl">
                                  <a href="#!"><img class="avatar-img rounded-circle" src="img/avatar/03.jpg"
                                      alt=""></a>
                                </div>
                                <h6 class="card-title mb-1 mt-3"> <a href="#!"> Samuel Bishop </a></h6>
                                <p class="mb-0 small lh-sm">22 mutual connections</p>
                              </div>
                              <!-- Card footer -->
                              <div class="card-footer p-2 border-0">
                                <button class="btn btn-sm btn-primary" data-bs-toggle="tooltip" data-bs-placement="top"
                                  title="Send message"> <i class="bi bi-chat-left-text"></i> </button>
                                <button class="btn btn-sm btn-danger" data-bs-toggle="tooltip" data-bs-placement="top"
                                  title="Remove friend"> <i class="bi bi-person-x"></i> </button>
                              </div>
                            </div>
                            <!-- Friends item END -->
                          </div>

                          <div class="col-6">
                            <!-- Friends item START -->
                            <div class="card shadow-none text-center h-100">
                              <!-- Card body -->
                              <div class="card-body p-2 pb-0">
                                <div class="avatar avatar-xl">
                                  <a href="#!"><img class="avatar-img rounded-circle" src="img/avatar/04.jpg"
                                      alt=""></a>
                                </div>
                                <h6 class="card-title mb-1 mt-3"> <a href="#"> Bryan Knight </a></h6>
                                <p class="mb-0 small lh-sm">1 mutual connection</p>
                              </div>
                              <!-- Card footer -->
                              <div class="card-footer p-2 border-0">
                                <button class="btn btn-sm btn-primary" data-bs-toggle="tooltip" data-bs-placement="top"
                                  title="Send message"> <i class="bi bi-chat-left-text"></i> </button>
                                <button class="btn btn-sm btn-danger" data-bs-toggle="tooltip" data-bs-placement="top"
                                  title="Remove friend"> <i class="bi bi-person-x"></i> </button>
                              </div>
                            </div>
                            <!-- Friends item END -->
                          </div>

                          <div class="col-6">
                            <!-- Friends item START -->
                            <div class="card shadow-none text-center h-100">
                              <!-- Card body -->
                              <div class="card-body p-2 pb-0">
                                <div class="avatar avatar-xl">
                                  <a href="#!"><img class="avatar-img rounded-circle" src="img/avatar/05.jpg"
                                      alt=""></a>
                                </div>
                                <h6 class="card-title mb-1 mt-3"> <a href="#!"> Amanda Reed </a></h6>
                                <p class="mb-0 small lh-sm">15 mutual connections</p>
                              </div>
                              <!-- Card footer -->
                              <div class="card-footer p-2 border-0">
                                <button class="btn btn-sm btn-primary" data-bs-toggle="tooltip" data-bs-placement="top"
                                  title="Send message"> <i class="bi bi-chat-left-text"></i> </button>
                                <button class="btn btn-sm btn-danger" data-bs-toggle="tooltip" data-bs-placement="top"
                                  title="Remove friend"> <i class="bi bi-person-x"></i> </button>
                              </div>
                            </div>
                            <!-- Friends item END -->
                          </div>

                        </div>
                      </div>
                      <!-- Card body END -->
                    </div>
                  </div>
                  <!-- Card END -->
                </div>
              </div>
              <!-- Right sidebar END -->

            </div> <!-- Row END -->
          </div>
          <!-- Container END -->
        </div>
      </main>




      <!-- Modal -->
      <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5" id="exampleModalLabel">Thông tin</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <form id="ffForm">
                <div class="form-group">
                  <textarea type="text" class="form-control" id="description" name="description" placeholder="Giới thiệu ngắn..."
                    required></textarea>
                </div>
                <div class="form-group">
                  <input type="text" class="form-control" id="tel" name="tel" placeholder=" " required>
                  <label for="tel" class="form-label">Liên hệ</label>
                </div>
                <div class="form-group">
                  <input type="text" class="form-control" id="email" name="email" placeholder=" " required>
                  <label for="email" class="form-label">Email</label>
                </div>
              </form>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary" id="btnSaveProfile">Save changes</button>
            </div>
          </div>
        </div>
      </div>
      <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
      <script>
        let email = "${userModel.email}";
        let tel ="${userModel.tel}";
        let description ="${userModel.description}";
         let profileEdit =  document.getElementById('profileEdit');
         profileEdit.addEventListener('click', function (){
           if(email!=null&& email.trim().length > 0){
             let emailInput = document.getElementById('email');
             emailInput.value = email;
             emailInput.setAttribute('readOnly', true);
           }
           if(tel!=null && tel.trim().length>0){
             let telInput = document.getElementById('tel');
             telInput.value = tel;
           }
           if(description!=null &&description.trim().length > 0){
             document.getElementById('description').value = description;
           }
         });
      </script>
      <script>
        $(document).ready(function (){
          $('#btnSaveProfile').click(function (e){
            e.preventDefault();
            let data = {};
            let formData = $('#ffForm').serializeArray();
            $.each(formData, function (i,v){
              data[""+v.name+""] = v.value;
            });
            $.ajax({
              url : 'edit-profile',
              method : 'put',
              contentType : 'application/json',
              data : JSON.stringify(data),
              dataType: 'json',
              success: function (result) {
                if(result.message ==="success"){
                  window.location.href ="/profile";
                }
              },
              error:function (){
                window.location.href = "/profile?message=error-edit-profile&alert=danger";
              }
            })

          });
        })
      </script>


      <script>

        document.querySelectorAll('[data-autoresize]').forEach(function (element) {
          var offset = element.offsetHeight - element.clientHeight;
          element.addEventListener('input', function (event) {
            event.target.style.height = 'auto';
            event.target.style.height = event.target.scrollHeight + offset + 'px';
          });
        });
      </script>
      <script>
        function getParameterByName(name){
          const  url = new URL(window.location.href);
          return url.searchParams.get(name);

        }
        const statusValue = getParameterByName('status');
        if(statusValue != null){
          document.getElementById('select-post-status').value = statusValue;
        }
        document.getElementById('select-post-status').addEventListener('change', function (){
            let selectValue = this.value;
            let url = '/profile';
            if(selectValue ==="All"){
              window.location.href = url;
            }else{
              window.location.href = url + "?status=" + selectValue;
            }
        });
      </script>


      <script src="<c:url value='/public/web/js/token/refreshToken.js'/> "></script>
      <script src="<c:url value='/public/web/js/bootstrap.bundle.min.js'/>"></script>
      <script src="<c:url value='/public/web/js/customize.js'/> "></script>
      <script src="<c:url value='/public/web/js/loginDirection/loginDirection.js'/> "></script>




    </body>

    </html>