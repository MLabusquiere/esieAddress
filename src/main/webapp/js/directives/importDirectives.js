var module = angular.module('esieAddress.directives');

// Inspired from https://github.com/clouddueling/angularjs-drag-n-drop/blob/master/directive.js
module.directive('ngFileUpload', function () {
	// Helper function that formats the file sizes
	function formatFileSize(bytes) {
		if (typeof bytes !== 'number') {
			return '';
		}

		if (bytes >= 1000000000) {
			return (bytes / 1000000000).toFixed(2) + ' GB';
		}

		if (bytes >= 1000000) {
			return (bytes / 1000000).toFixed(2) + ' MB';
		}

		return (bytes / 1000).toFixed(2) + ' KB';
	}

	return {
		restrict: 'A',
		link: function (scope, elem, attr, ImportCtrl) {
			var dragForm = "<form id='file-upload' method='post' action='rest/import' enctype='multipart/form-data'>\
				<div id='drop'>\
					<h4>Drop Here</h4><br>\
					<a class='btn btn-primary'>Browse</a>\
					<input type='file' multiple />\
				</div>\
				<ul>\
				</ul>\
				</form>";

			elem.html(dragForm);

			var ul = $('#file-upload ul');

			$(document).on('click', '#drop a', function () {
				// Simulate a click on the file input button
				// to show the file browser dialog
				$(this).parent().find('input').click();
			});

			// Initialize the jQuery File Upload plugin
			$('#file-upload').fileupload({
				// This element will accept file drag/drop uploading
				dropZone: $('#drop'),

				// This function is called when a file is added to the queue;
				// either via the browse button, or via drag/drop:
				add: function (e, data) {
					var tpl = $('' +
						'<li class="working">' +
						/*'	<div class="progress">' +
						 '		<div id="loadbar" class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">' +
						 '			<span class="sr-only">60% Complete</span>' +
						 '		</div>' +
						 '	</div>' +*/
						/*'	<input type="text" value="0" data-width="48" data-height="48" data-fgColor="#0788a5" data-readOnly="1" data-bgColor="#3e4043" />' +*/
						'	<p></p>' +
						'	<span></span>' +
						'</li>');

					// Append the file name and file size
					tpl.find('p').text(data.files[0].name)
						.append('<i>' + formatFileSize(data.files[0].size) + '</i>');

					// Add the HTML to the UL element
					data.context = tpl.appendTo(ul);

					// Initialize the knob plugin
					tpl.find('input').knob();

					// Listen for clicks on the cancel icon
					tpl.find('span').click(function () {
						if (tpl.hasClass('working')) {
							jqXHR.abort();
						}

						tpl.fadeOut(function () {
							tpl.remove();
						});
					});

					// Automatically upload the file once it is added to the queue
					var jqXHR = data.submit();
				},

				done: function (e, data) {
					scope.$apply(function (s) {
						s.$eval(attr.complete);
					});
				},

				progress: function (e, data) {
					// Calculate the completion percentage of the upload
					var progress = parseInt(data.loaded / data.total * 100, 10);

					// Update the hidden input field and trigger a change
					// so that the jQuery knob plugin knows to update the dial
					// data.context.find('#loadbar').val(progress).change();
					/*data.context.find('#loadbar').getAttribute('aria-valuenow').val(progress).change();

					 if (progress == 100) {
					 data.context.find('#loadbar').addClass('progress-bar-success');
					 }*/
				},

				fail: function (e, data) {
					scope.$apply(function (s) {
						s.$eval(attr.error);
					});

					data.context.addClass('error');
				}
			});

			// Prevent the default action when a file is dropped on the window
			$(document).on('dragover', function (e) {
				e.preventDefault();
				$('#drop').addClass('active');
			});

			$(document).on('drop dragleave', function (e) {
				e.preventDefault();
				$('#drop').removeClass('active');
			});
		}
	}
});