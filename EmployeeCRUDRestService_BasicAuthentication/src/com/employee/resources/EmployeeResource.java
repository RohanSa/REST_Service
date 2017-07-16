package com.employee.resources;

import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import com.employee.dao.EmployeeStore;
import com.employee.dto.EmployeeDto;
import com.employee.service.EmployeeService;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("employees")
public class EmployeeResource {

	private EmployeeStore employeeStore = new EmployeeStore();

	private SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");

	private EmployeeService employeeService = new EmployeeService();

	private final String uploadedFileLocation = "D:\\employeeresume";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllEmployees() {

		return Response.ok(employeeStore.getAllEmployees()).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getEmployeeByID(@PathParam("id") int id) {

		return Response.ok(employeeStore.getEmployeeById(id)).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response createNewEmployee(@FormDataParam("name") String name,
			@FormDataParam("dateofbirth") String dateOfBirth, @FormDataParam("resume") InputStream uploadedInputStream,
			@FormDataParam("resume") FormDataContentDisposition fileDetail) {

		System.out.println("name : " + name);
		System.out.println("date of Birth : " + dateOfBirth);
		System.out.println("File name : " + fileDetail.getFileName());

		Date dOfBirth = null;
		try {
			dOfBirth = dateformatter.parse(dateOfBirth);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}

		int id = employeeStore.addNewEmployee(new EmployeeDto(0, name, dOfBirth));

		if (uploadedInputStream != null)
			employeeService.writeToFile(uploadedInputStream, uploadedFileLocation, id, fileDetail.getFileName());

		return Response.ok().build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response updateEmployee(@FormParam("id") int id, @FormParam("name") String name,
			@FormParam("dateofbirth") String dateOfBirth) {

		System.out.println("id : " + id);
		System.out.println(" name : " + name);
		System.out.println(" date of birth : " + dateOfBirth);

		if (employeeStore.getEmployeeById(id) == null)
			return Response.status(Status.BAD_REQUEST).build();

		Date dOfBirth = null;
		try {
			dateformatter.setLenient(false);
			dOfBirth = dateformatter.parse(dateOfBirth);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		EmployeeDto employeeDto = employeeStore.getEmployeeById(id);
		employeeDto.setDateOfBirth(dOfBirth);
		
		if (name != null && name.matches("[a-zA-Z]+"))
			employeeDto.setName(name);

		boolean result = employeeStore.updateEmployee(id, employeeDto);

		if (result == false)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok().build();
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response updateEmployee(@PathParam("id") int id) {

		System.out.println("Employee id to delete is : " + id);
		boolean result = employeeStore.removeEmployee(id);

		if (result == false)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("resume")
	public Response downloadResume(@QueryParam("id") int id) {

		if (employeeStore.getEmployeeById(id) != null) {

			if ((employeeService.getEmployeeResume(uploadedFileLocation, id)) != null) {
				File resume = employeeService.getEmployeeResume(uploadedFileLocation, id);
				System.out.println(this.getClass().getName() + " : resume.getName() :" + resume.getName());
				ResponseBuilder response = Response.ok(resume);
				response.header("Content-Disposition", "attachment; filename=" + resume.getName());
				return response.build();
			} else {
				return Response.status(Status.NO_CONTENT).build();
			}

		}
		return Response.status(Status.NOT_FOUND).build();

	}

}
