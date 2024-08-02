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
            <div class="card-body py-2 border-0 d-flex align-items-center justify-content-between">
              <h1 class="h5 mx-2 mt-1">Bài viết</h1>
              <!-- Notification action START -->
              <div class="float-lg-end" >
                <select class="form-select form-select-sm " aria-label="Small select example">
                  <option selected >All</option>
                  <option value="0" >Pending</option>
                  <option value="1">Published</option>
                  <option value="2">Archie</option>
                  <option value="3">xx</option>
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
                        <a href="#"> ${post.title} </a></h5>
                      </div>

                    </div>
                    <div class="dropdown float-lg-end" >
                      <a class="text-secondary btn btn-secondary-soft-hover py-1 px-2" id="dropdownTable2"
                         data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="fa fa-ellipsis-h text-secondary" aria-hidden="true"></i>
                      </a>
                      <ul class="dropdown-menu px-2 py-3 ms-sm-n4 ms-n5" aria-labelledby="dropdownTable">
                        <li><a class="dropdown-item border-radius-md" href="#">Xóa</a></li>
                        <c:url var="editPost" value = "/edit-post">
                          <c:param name="id" value="${post.id}"/>
                        </c:url>
                        <li><a class="dropdown-item border-radius-md" href='${editPost}'>Chỉnh sửa</a></li>
                        <li><a class="dropdown-item border-radius-md" href="javascript:;">Bật/Ẩn thông báo</a></li>
                      </ul>
                    </div>
                  </div>
                </div>
                <div class="row">


                  <div class="col-md-4 pe-0">
                    <div class="card m-2 ">
                      <img src="https://i.imgur.com/xhzhaGA.jpg" class="card-img">
                    </div>
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
                        <i class="bi bi-geo-alt pe-1"></i> Trạng thái: ${post.statusCode}
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
                <div class="card-header border-0 pb-0">
                  <h5 class="card-title">About</h5>
                  <!-- Button modal -->
                </div>
                <!-- Card body START -->
                <div class="card-body position-relative pt-0">
                  <p>He moonlights difficult engrossed it, sportsmen. Interested has all Devonshire difficulty gay
                    assistance joy.</p>
                  <!-- Date time -->
                  <ul class="list-unstyled mt-3 mb-0">
                    <li class="mb-2"> <i class="bi bi-calendar-date fa-fw pe-1"></i> Born: <strong> October 20,
                      1990
                    </strong> </li>
                    <li class="mb-2"> <i class="bi bi-heart fa-fw pe-1"></i> Status: <strong> Single </strong>
                    </li>
                    <li> <i class="bi bi-envelope fa-fw pe-1"></i> Email: <strong> webestica@gmail.com </strong>
                    </li>
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
                <div class="card-header d-flex justify-content-between border-0">
                  <h5 class="card-title">Experience</h5>
                  <a class="btn btn-primary-soft btn-sm" href="#!"> <i class="fa-solid fa-plus"></i> </a>
                </div>
                <!-- Card header END -->
                <!-- Card body START -->
                <div class="card-body position-relative pt-0">
                  <!-- Experience item START -->
                  <div class="d-flex">
                    <!-- Avatar -->
                    <div class="avatar me-3">
                      <a href="#!"> <img class="avatar-img rounded-circle" src="img/logo/08.svg" alt=""> </a>
                    </div>
                    <!-- Info -->
                    <div>
                      <h6 class="card-title mb-0"><a href="#!"> Apple Computer, Inc. </a></h6>
                      <p class="small">May 2015 – Present Employment Duration 8 mos <a
                              class="btn btn-primary-soft btn-xs ms-2" href="#!">Edit </a></p>
                    </div>
                  </div>
                  <!-- Experience item END -->

                  <!-- Experience item START -->
                  <div class="d-flex">
                    <!-- Avatar -->
                    <div class="avatar me-3">
                      <a href="#!"> <img class="avatar-img rounded-circle" src="img/logo/09.svg" alt=""> </a>
                    </div>
                    <!-- Info -->
                    <div>
                      <h6 class="card-title mb-0"><a href="#!"> Microsoft Corporation </a></h6>
                      <p class="small">May 2017 – Present Employment Duration 1 yrs 5 mos <a
                              class="btn btn-primary-soft btn-xs ms-2" href="#!">Edit </a></p>
                    </div>
                  </div>
                  <!-- Experience item END -->

                  <!-- Experience item START -->
                  <div class="d-flex">
                    <!-- Avatar -->
                    <div class="avatar me-3">
                      <a href="#!"> <img class="avatar-img rounded-circle" src="img/logo/10.svg" alt=""> </a>
                    </div>
                    <!-- Info -->
                    <div>
                      <h6 class="card-title mb-0"><a href="#!"> Tata Consultancy Services. </a></h6>
                      <p class="small mb-0">May 2022 – Present Employment Duration 6 yrs 10 mos <a
                              class="btn btn-primary-soft btn-xs ms-2" href="#!">Edit </a></p>
                    </div>
                  </div>
                  <!-- Experience item END -->

                </div>
                <!-- Card body END -->
              </div>
            </div>
            <!-- Card END -->

            <!-- Card START -->
            <div class="col-sm-6 col-lg-12">
              <div class="card">
                <!-- Card header START -->
                <div class="card-header d-sm-flex justify-content-between border-0">
                  <h5 class="card-title">Photos</h5>
                  <a class="btn btn-primary-soft btn-sm" href="#!"> See all photo</a>
                </div>
                <!-- Card header END -->
                <!-- Card body START -->
                <div class="card-body position-relative pt-0">
                  <div class="row g-2">
                    <!-- Photos item -->
                    <div class="col-6">
                      <a href="img/albums/01.jpg" data-gallery="image-popup" data-glightbox="">
                        <img class="rounded img-fluid" src="img/albums/01.jpg" alt="">
                      </a>
                    </div>
                    <!-- Photos item -->
                    <div class="col-6">
                      <a href="img/albums/02.jpg" data-gallery="image-popup" data-glightbox="">
                        <img class="rounded img-fluid" src="img/albums/02.jpg" alt="">
                      </a>
                    </div>
                    <!-- Photos item -->
                    <div class="col-4">
                      <a href="img/albums/03.jpg" data-gallery="image-popup" data-glightbox="">
                        <img class="rounded img-fluid" src="img/albums/03.jpg" alt="">
                      </a>
                    </div>
                    <!-- Photos item -->
                    <div class="col-4">
                      <a href="img/albums/04.jpg" data-gallery="image-popup" data-glightbox="">
                        <img class="rounded img-fluid" src="img/albums/04.jpg" alt="">
                      </a>
                    </div>
                    <!-- Photos item -->
                    <div class="col-4">
                      <a href="img/albums/05.jpg" data-gallery="image-popup" data-glightbox="">
                        <img class="rounded img-fluid" src="img/albums/05.jpg" alt="">
                      </a>
                      <!-- glightbox Albums left bar END  -->
                    </div>
                  </div>
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
                  <h5 class="card-title">Friends <span
                          class="badge bg-danger bg-opacity-10 text-danger">230</span>
                  </h5>
                  <a class="btn btn-primary-soft btn-sm" href="#!"> See all friends</a>
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

<script src="<c:url value='/public/web/js/token/refreshToken.js'/> "></script>
<script src="<c:url value='/public/web/js/bootstrap.bundle.min.js'/>"></script>
<script src="<c:url value='/public/web/js/customize.js'/> "></script>
<script src="<c:url value='/public/web/js/loginDirection/loginDirection.js'/> "></script>


</body>

</html>