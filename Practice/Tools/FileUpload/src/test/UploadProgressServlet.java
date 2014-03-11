package test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

public class UploadProgressServlet extends HttpServlet {

	private static final long serialVersionUID = 5151364782256414593L;
	/**
	 * doPost process file upload
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		// get upload time
		// will use it as an ID later
		String time = req.getParameter("time");
		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = newDiskFileItemFactory(servletContext, repository);
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// add progress listener listening to upload progress
		upload.setProgressListener(getProgressListener(time, req.getSession()));

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
			// remove progress value
			req.getSession().removeAttribute(time);
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * doGet report upload progress
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		// get upload time
		String time = req.getParameter("time");
		// get progress
		// null: already finished
		Object o = req.getSession().getAttribute(time);
		String progress =  o == null? "100.0" : o+"";

		if (progress.startsWith("100")) { // just done
			req.getSession().removeAttribute(time);
		}
		// build response
		StringBuilder sb = new StringBuilder("");
		sb.append("{progress: {")
			.append(time).append(":").append(progress)
			.append("}}");
		System.out.println(sb.toString());
		PrintWriter out = resp.getWriter();
		// response data
		out.println(sb.toString());
		out.close();
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
	// progress listener
	// put progress into session with the given id
	private static ProgressListener getProgressListener (final String id, final HttpSession sess) {
		ProgressListener progressListener = new ProgressListener(){
			public void update(long pBytesRead, long pContentLength, int pItems) {
				// put progress into session
				sess.setAttribute(id, ((double)pBytesRead / (double)pContentLength) * 100);
			}
		};
		return progressListener;
	}
}