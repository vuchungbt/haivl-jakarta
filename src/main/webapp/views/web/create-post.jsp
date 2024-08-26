<%@include file="/common/taglib.jsp" %>
 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:url var="createPost" value="/create-post"/>
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
    <form id = "formSubmit" enctype="multipart/form-data">
          <!-- Container START -->
          <div class="container">
            <div class="row g-4">

        <!-- Main content START -->
        <div class="col-lg-8 vstack gap-4">
          <!-- Card START -->
          <div class="card card-body">
            <!-- Post input -->
            <div class="w-100" >
                <input type="hidden" id="currentImagePath" name="currentImagePath" value="${postModel.imagePath}" />
                <input type="hidden" id="imageAction" name="imageAction" value="keep" />
              <textarea  class="form-control pe-4 border-0" rows="1" maxlength="100" data-autoresize=""
                       name="title" placeholder="Title...">${postModel.title}</textarea>
                <textarea maxlength="1200" class="form-control pe-4 border-0" rows="2" data-autoresize=""
                          name="content" placeholder="Content...">${postModel.content}</textarea>
                <div>

                    <div>
                        <c:if test="${empty postModel.imagePath}">
                            <input type="file" id="input-file-to-destroy" class="dropify"
                                    data-max-file-size="25M" data-max-height="3000" name="image" src="" />
                        </c:if>
                        <c:if test="${not empty postModel.imagePath}">
                            <input type="file" id="input-file-to-destroy" class="dropify"
                                   data-default-file="/post-image-api?path=${postModel.imagePath}" data-max-file-size="25M"
                                   data-max-height="3000" name="image" src="" />
                        </c:if>
                    </div>

                    <c:if test="${empty postModel.id}">
                        <input type="button" id="btnSubmitPost" class="btn btn-sm btn-info" style="margin:5px; float:right" value = "Post" />
                    </c:if>


                    <c:if test="${not empty postModel.id}">
                        <input type="button" id="btnSubmitPost" class="btn btn-sm btn-info" style="margin:5px; float:right" value = "Update" />
                    </c:if>

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
                            name="tag-collect" id="tagInput" placeholder="#Tag"><c:if test="${not empty postModel.tab}"><c:forEach var="tag" items="${postModel.tab}">#${tag} </c:forEach></c:if></textarea>
<%--                            <ul id="suggestions" class="list-group position-absolute" style="display: none; max-height: 100px; overflow-y: auto;"></ul>--%>
                            <ul id="suggestions" class="dropdown-menu text-small" ></ul>

                            <div class="mt-0">

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
                <input type="hidden" id ="id" name="id" value="${postModel.id}"/>
    </form>
        <div id = "result-message" class="alert alert-danger text-center " style="display: none"></div>
            <input type="hidden" id="tagNames" value="${tagNames}">

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
                  if(confirm("Do you really want to delete \"" + element.file.name + "\" ?")){
                      $('#imageAction').val('delete');
                  }else{
                      return false;
                  }

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
    <script>
        $('#btnSubmitPost').click(function(e){
           e.preventDefault();
           let formData = new FormData($('#formSubmit')[0]);

           let id = $('#id').val();
           if(id ===""){
               addPost(formData);
           }

           else{
               updatePost(formData);
           }
           function addPost(formData){
               $.ajax({
                   url : '/create-post',
                   type : 'post',
                   data : formData,
                   processData: false,
                   contentType: false,
                   // dataType : 'json',
                   success : function(result){
                       if(result.status ==="success"){
                           window.location.href = "/profile?alert=success&message=create-post-success";
                       }else{
                            $('#result-message').removeClass("alert-success").addClass("alert-danger")
                                .html('<i class="ace-icon fa fa-exclamation-triangle red bigger-130"></i> ' + result.message).show();
                       }
                   },
                   error: function (){
                        $('#result-message').removeClass("alert-success").addClass("alert-danger")
                            .html('<i class="ace-icon fa fa-exclamation-triangle red bigger-130"></i> ' +
                                '<div>Đã có lỗi xảy ra, vui lòng thử lại vào thời điểm khác.</div>' +
                                '<a href="/"><b>Trở về trang chủ</b></a>').show();
                   },
               })

           }
           function updatePost(formData){
                $.ajax({
                    url : '/edit-post',
                    type : 'put',
                    data : formData,
                    processData: false,
                    contentType: false,
                    success : function(result){
                        if(result.status ==="success"){
                            window.location.href = "/profile?alert=success&message=edit-post-success";
                        }else{
                            $('#result-message').removeClass("alert-success").addClass("alert-danger")
                                .html('<i class="ace-icon fa fa-exclamation-triangle red bigger-130"></i> ' + result.message).show();
                        }
                    },
                    error: function (){
                        $('#result-message').removeClass("alert-success").addClass("alert-danger")
                            .html('<i class="ace-icon fa fa-exclamation-triangle red bigger-130"></i> ' +
                                '<div>Đã có lỗi xảy ra, vui lòng thử lại vào thời điểm khác.</div>' +
                                '<a href="/"><b>Trở về trang chủ</b></a>').show();
                    },
                })

            }

        });
    </script>
    <script>
        let tagNames = ${tagNames};
        let tagInput = document.getElementById('tagInput');

        let suggestions = document.getElementById('suggestions');

        tagInput.addEventListener('input', function () {

            const cursorPosition = tagInput.selectionStart;
            const queryUpToCursor = tagInput.value.substring(0, cursorPosition);
            const lastHashIndex1 = queryUpToCursor.lastIndexOf(' ');
            const lastHashIndex = queryUpToCursor.lastIndexOf('#');

            let searchTerm="";
            if(lastHashIndex1 < lastHashIndex){
                searchTerm = queryUpToCursor.substring(lastHashIndex + 1, cursorPosition);
            }

           suggestions.innerHTML = '';
            if(searchTerm.length > 0){
                let matches = tagNames.filter(tag => tag.startsWith(searchTerm)).slice(0, 5);
               if(matches.length > 0){
                   suggestions.style.display = 'block';
                   matches.forEach(math => {
                       const li = document.createElement('li');
                       li.classList.add('dropdown-item');
                       li.textContent = math;
                       li.addEventListener('click', function (){
                           const tagValue = tagInput.value;
                           const beforeCursor = tagValue.substring(0, lastHashIndex);
                           const afterCursor = tagValue.substring(cursorPosition);

                           tagInput.value = beforeCursor + '#' + this.textContent + afterCursor;
                           
                           suggestions.style.display = 'none';
                       });
                       suggestions.appendChild(li);
                   })
               }else {
                   suggestions.style.display = 'none';
               }
           }else {
               suggestions.style.display = 'none';
           }
        });



    </script>
    </body>

    </html>