package test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 5151364782256414593L;
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		
		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = newDiskFileItemFactory(servletContext, repository);
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Parse the request
		try {
			List<FileItem> items = upload.parseRequest(req);
			for (FileItem item : items) {
				if (item.isFormField()) {
					// output value of the 'note' field in the form
					System.out.println(item.getString());
				} else {
					// store uploaded file to /WEB-INF/uploadedFiles
					File f = new File(servletContext.getRealPath("/")
							+ File.separator + "WEB-INF"
							+ File.separator + "uploadedFiles"
							+ File.separator + item.getName());
					item.write(f);
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// create DiskFileItemFactory with fileCleaningTracker
	private static DiskFileItemFactory newDiskFileItemFactory(ServletContext context,
			File repository) {
		FileCleaningTracker fileCleaningTracker
			= FileCleanerCleanup.getFileCleaningTracker(context);
		DiskFileItemFactory factory
			= new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,
					repository);
		factory.setFileCleaningTracker(fileCleaningTracker);
		return factory;
	}
}