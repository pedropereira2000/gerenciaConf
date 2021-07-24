package Dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import Bean.Reserva;
import Connection.ConnectionFactory;
import Dao.PeriodoReservadoDAO;

class TestePeriodoReservadoDao {

	@Test
	void test001() {
		var periRes = new PeriodoReservadoDAO();
		List<Reserva> res = periRes.read("24-07-2021","H","001");
		
	}

}
