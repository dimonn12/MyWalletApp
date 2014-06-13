package com.mywalletapp.core.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mywalletapp.core.database.DatabaseSourceWrapper;

@Controller
@RequestMapping("/login")
public class LoginController {

	private final DatabaseSourceWrapper source;
	
	public LoginController(DatabaseSourceWrapper source)
	{
		this.source = source;
	}


/*    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Appointment> get() {
        return appointmentBook.getAppointmentsForToday();
    }*/

}
