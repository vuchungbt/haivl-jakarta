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
            <c:set var="method" value="${empty postModel.id ? 'post' : 'put'}"/>
            <c:if test="${empty postModel.id}">
                <c:url var="postUrl" value="/create-post"/>
            </c:if>
            <c:if test="${not empty postModel.id}">
                <c:url var="postUrl" value="/edit-post"/>
            </c:if>
            <form id = "formSubmit" enctype="multipart/form-data" action="${postUrl}" method="post">
                <input type="hidden" name="_method" value="${method}" />

          <!-- Container START -->
          <div class="container">
            <div class="row g-4">

        <!-- Main content START -->
        <div class="col-lg-8 vstack gap-4">
          <!-- Card START -->
          <div class="card card-body">
            <!-- Post input -->
            <div class="w-100" >
              <textarea  class="form-control pe-4 border-0" rows="1" maxlength="100" data-autoresize=""
                       name="title" placeholder="Title...">${postModel.title}</textarea>
                <textarea maxlength="1200" class="form-control pe-4 border-0" rows="2" data-autoresize=""
                          name="content" placeholder="Content...">${postModel.content}</textarea>
                <div>

                    <div>
                        <!--data-default-file  just to view -->
<%--                        <input type="file" id="input-file-to-destroy" class="dropify"--%>
<%--                               data-default-file="https://i.imgur.com/xhzhaGA.jpg" data-max-file-size="25M"--%>
<%--                               data-max-height="3000" name="image" src="${postModel.imagePath}" />--%>
                        <input type="file" id="input-file-to-destroy" class="dropify"
                               data-default-file="${postModel.imagePath}" data-max-file-size="25M"
                               data-max-height="3000" name="image" src="" />
                    </div>


                  <button type="submit" id="btnCreatePost" class="btn btn-sm btn-info" style="margin:5px; float:right" >
                    <c:if test="${empty postModel.id}"> Post </c:if>
                    <c:if test="${not empty postModel.id}"> Update </c:if>
                  </button>

                </div>
            </div>
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

                            <textarea id= "mainSourceName" data-autoresize="" class="form-control pe-5 bg-light" rows="1"
                              placeholder="Source name" name="sourceName">${postModel.sourceName}</textarea>
                            <a type button id = "setSource"
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
                            name="tag-collect" placeholder="#Tag"></textarea>
                          <div class="mt-0">
                            <c:forEach var="tag" items="${postModel.tags}">
                                <span class="badge bg-primary me-1">${tag}</span>
                            </c:forEach>
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
                  <input type="text" id="sourceNameModal" class="form-control m-1" placeholder="Source name">
                  <input type="text" class="form-control m-1" id="sourceLinkModal" name = "source" placeholder="Source link" value="${postModel.source}">
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                  <button type="button" id="saveChangesBtnModal" data-bs-dismiss="modal" class="btn btn-primary">Save changes</button>
                </div>
              </div>
            </div>
          </div>
                <input type="hidden" name="id" value="${postModel.id}"/>
        </form>
        <span id = "result-message" class="alert alert-danger text-center " style="display: none">
                  <i class="ace-icon fa fa-exclamation-triangle red bigger-130"></i>
                  <div>Đã có lỗi xảy ra, vui lòng thử lại vào thời điểm khác.</div>
                  <a href="/"><b>Trở về trang chủ</b></a>
        </span>

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

        let originalSourceNameValue;
        let originalSourceLinkValue;
        document.getElementById('setSource').addEventListener('click', function (){
            originalSourceNameValue= document.getElementById('mainSourceName').value;
            originalSourceLinkValue = document.getElementById('sourceLinkModal').value;

            const text = document.getElementById('sourceNameModal');
            text.value = originalSourceNameValue;

        });
        document.getElementById('saveChangesBtnModal').addEventListener('click', function() {

            originalSourceNameValue = document.getElementById('sourceNameModal').value;
            originalSourceLinkValue = document.getElementById('sourceLinkModal').value;

        });

        let exampleModal = document.getElementById('exampleModal');
        exampleModal.addEventListener('hidden.bs.modal', function (){
            document.getElementById('mainSourceName').value = originalSourceNameValue;
            document.getElementById('sourceLinkModal').value = originalSourceLinkValue;
        });

    </script>
    </body>

    </html>