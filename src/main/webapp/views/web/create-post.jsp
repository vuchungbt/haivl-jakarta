<%@include file="/common/taglib.jsp" %>
  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
      <!-- Required meta tags -->
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <c:if test="${empty postModel.id}">
        <title>Create Post</title>
      </c:if>
      <c:if test="${not empty postModel.id}">
        <title>Update Post</title>
      </c:if>

      <!-- Bootstrap CSS -->
      <%-- <link rel="stylesheet" href="<c:url value='/template/web/css/bootstrap.min.css' />" />--%>
      <%-- <link rel="stylesheet" href="<c:url value='/template/web/css/customize.css' />" />--%>
      <%-- <link rel="stylesheet" href="<c:url value='/template/web/css/dropify.min.css' />" />--%>

      <%-- <script src="<c:url value='/template/web/js/kit.42d5adcbca.js' />"></script>--%>
        <%-- <!-- Dark mode -->--%>
          <%-- <script src="<c:url value='/template/web/js/theme.js' />"></script>--%>

            <style>
              textarea {
                width: 100%;
                overflow: hidden;
                resize: none;
              }
            </style>
    </head>


    <body>

      <%-- <%@ include file="/common/web/header-bar.jsp" %>--%>

        <!-- **************** MAIN CONTENT START **************** -->
        <main class="py-2">

          <!-- Container START -->
          <div class="container">
            <div class="row g-4">

              <!-- Main content START -->
              <div class="col-lg-8 vstack gap-4">
                <!-- Card START -->
                <div class="card card-body">
                  <!-- Post input -->
                  <form id="formSubmit" enctype="multipart/form-data" class="w-100" action="">
                    <textarea class="form-control pe-4 border-0" rows="1" maxlength="100" data-autoresize=""
                      name="title" placeholder="Title...">${postModel.title}</textarea>
                    <textarea maxlength="1200" class="form-control pe-4 border-0" rows="2" data-autoresize=""
                      name="content" placeholder="Content...">${postModel.content}</textarea>
                    <div>
                      <!--data-default-file  just to view -->
                      <input type="file" id="input-file-to-destroy" class="dropify"
                        data-default-file="https://i.imgur.com/xhzhaGA.jpg" data-max-file-size="25M"
                        data-max-height="3000" name="image" src="${postModel.imagePath}" />

                    </div>



                    <c:if test="${empty postModel.id}">
                      <button type="button" id="btnCreatePost" class="btn btn-sm btn-info"
                        style="margin:5px; float:right">Post</button>
                    </c:if>
                    <c:if test="${not empty postModel.id}">
                      <button type="button" id="btnCreatePost" class="btn btn-sm btn-info"
                        style="margin:5px; float:right">Update</button>
                    </c:if>


                  </form>
                  <div id="result-message" class="alert text-center " style="display: none;"></div>


                  <!-- Share feed toolbar END -->
                </div>
              </div>
              <!-- Main content END -->

              <!-- Right sidebar START -->
              <div class="col-lg-4">

                <div class="row g-4">

                    <div class="col-sm-6 col-lg-12">
     <!-- Card START -->
                      <div class="card">
                        <div class="card-body d-flex" >

                          <div class="nav nav-item w-100 position-relative">

                            <textarea data-autoresize="" class="form-control pe-5 bg-light" rows="1"
                              placeholder="Source name...(chua lam)"></textarea>
                            <a type button
                              class="nav-link bg-transparent px-3 position-absolute top-50 end-0 translate-middle-y border-0"
                              data-bs-toggle="modal" data-bs-target="#exampleModal">
                              <i class="far fa-edit"></i>
                            </a>
                          </div>

                        </div>
                      </div>
                      </div>
                      <div class="col-sm-6 col-lg-12">
                      <!-- Card START -->
                      <div class="card">
                        <div class="card-body">

                          <textarea maxlength="500" class="form-control border-0" rows="1" data-autoresize=""
                            name="tag-collect" placeholder="#Tag...(chua lam)"></textarea>
                          <div class="mt-0">
                            <span class="badge bg-primary me-1">#tag1</span>
                            <span class="badge bg-secondary me-1">#tag2</span>
                            <span class="badge bg-success me-1">#tag3</span>
                          </div>
                        </div>
                      </div>
                      <!-- Card END -->
                    </div>

                </div>
              </div>
              <!-- Right sidebar END -->

            </div> <!-- Row END -->
          </div>
          <!-- Container END -->







          <!-- Modal -->
          <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
            aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <h1 class="modal-title fs-5" id="exampleModalLabel">Source</h1>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                  <input type="text" class="form-control m-1" placeholder="Source name">
                  <input type="text" class="form-control m-1" placeholder="Source link">
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                  <button type="button" class="btn btn-primary">Save changes</button>
                </div>
              </div>
            </div>
          </div>












        </main>

        <%-- <%@ include file="/common/web/footer.jsp" %>--%>

          <script src="<c:url value='/public/web/js/jquery-3.5.1.js' />"></script>
          <script src="<c:url value='/public/web/js/bootstrap.bundle.min.js' />"></script>
          <script src="<c:url value='/public/web/js/customize.js' />"></script>
          <script src="<c:url value='/public/web/js/dropify.js' />"></script>
          <script>
            $(document).ready(function () {
              // Basic
              $('.dropify').dropify();

              // Translated
              $('.dropify-fr').dropify({
                messages: {
                  default: 'Glissez-déposez un fichier ici ou cliquez',
                  replace: 'Glissez-déposez un fichier ou cliquez pour remplacer',
                  remove: 'Supprimer',
                  error: 'Désolé, le fichier trop volumineux'
                }
              });

              // Used events
              var drEvent = $('#input-file-events').dropify();

              drEvent.on('dropify.beforeClear', function (event, element) {
                return confirm("Do you really want to delete \"" + element.file.name + "\" ?");
              });

              drEvent.on('dropify.afterClear', function (event, element) {
                alert('File deleted');
              });

              drEvent.on('dropify.errors', function (event, element) {
                console.log('Has Errors');
              });

              var drDestroy = $('#input-file-to-destroy').dropify();
              drDestroy = drDestroy.data('dropify')
              $('#toggleDropify').on('click', function (e) {
                e.preventDefault();
                if (drDestroy.isDropified()) {
                  drDestroy.destroy();
                } else {
                  drDestroy.init();
                }
              })
            });
            document.querySelectorAll('[data-autoresize]').forEach(function (element) {
              var offset = element.offsetHeight - element.clientHeight;
              element.addEventListener('input', function (event) {
                event.target.style.height = 'auto';
                event.target.style.height = event.target.scrollHeight + offset + 'px';
              });
            });
          </script>
          <script>
            $('#btnCreatePost').click(function (e) {
              e.preventDefault();

              // Khởi tạo FormData từ form HTML
              let formData = new FormData($('#formSubmit')[0]);

              // Gửi dữ liệu bằng AJAX
              $.ajax({
                url: '/api-post',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function (result) {
                  console.log(result);
                  if (result.status === "success") {
                    window.location.href = "/profile";
                    // $('#formSubmit').hide();
                    // $('#result-message').removeClass('alert-danger').addClass('alert-success')
                    //         .html('<div>Chúc mừng bạn đã đăng bài thành công!</div> <br>' +
                    //                 '<a href="/view-all-post"><b>Xem bài vừa đăng</b></a>').show();
                  } else {
                    $('#result-message').removeClass('alert-success').addClass('alert-danger')
                      .html('<i class="ace-icon fa fa-exclamation-triangle red bigger-130"></i> ' + result.message).show();
                  }
                },
                error: function () {
                  $('#result-message').removeClass('alert-success').addClass('alert-danger')
                    .html('<i class="ace-icon fa fa-exclamation-triangle red bigger-130"></i> ' +
                      '<div>Đã có lỗi xảy ra, vui lòng thử lại vào thời điểm khác.</div>' +
                      '<a href="/"><b>Trở về trang chủ</b></a>').show();
                }
              });
            });
          </script>
    </body>

    </html>