package com.example;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

public class OrderItemPreparedStatementSetter implements ItemPreparedStatementSetter<Order> {

	@Override
	public void setValues(Order item, PreparedStatement ps) throws SQLException {

		ps.setLong(1, item.getOrderId());
		ps.setString(2, item.getFirstName());
		ps.setString(3, item.getLastName());
		ps.setString(4, item.getEmail());
		ps.setBigDecimal(5, item.getCost());
		ps.setString(6, item.getItemId());
		ps.setString(7, item.getItemName());
		ps.setDate(8,new Date(item.getShipDate().getTime()));
	}

}
