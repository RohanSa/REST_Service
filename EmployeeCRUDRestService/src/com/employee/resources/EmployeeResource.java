package com.employee.resources;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.employee.dao.EmployeeStore;
import com.employee.dto.EmployeeDto;

@Path("employees")
public class EmployeeResource {

	private EmployeeStore employeeStore = new EmployeeStore();

	private SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
	
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
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createNewEmployee(@FormParam("name") String name, @FormParam("dateofbirth") String dateOfBirth) {
		System.out.println("name : " + name);
		System.out.println("date of Birth : "+ dateOfBirth);
		
		Date dOfBirth = null;
		try {
			dOfBirth = dateformatter.parse(dateOfBirth);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		
		employeeStore.addNewEmployee(new EmployeeDto(0, name, dOfBirth));

		return Response.ok().build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response updateEmployee(@FormParam("id") int id, @FormParam("name") String name,
			@FormParam("dateofbirth") String dateOfBirth) {
		
		System.out.println("id : "+ id);
		System.out.println(" name : "+ name);
		System.out.println(" date of birth : "+ dateOfBirth);

		Date dOfBirth = null;
		try {
			dOfBirth = dateformatter.parse(dateOfBirth);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}

		
		EmployeeDto employeeDto = new EmployeeDto(id, name, dOfBirth);

		boolean result = employeeStore.updateEmployee(id, employeeDto);

		if (result == false)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok().build();
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response updateEmployee(@PathParam("id") int id) {

		System.out.println("Employee id to delete is : "+ id);
		boolean result = employeeStore.removeEmployee(id);

		if (result == false)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok().build();
	}

}
