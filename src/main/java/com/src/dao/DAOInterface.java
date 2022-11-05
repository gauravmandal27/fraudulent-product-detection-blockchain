package com.src.dao;

import java.util.ArrayList;

import com.src.model.Code;
import com.src.model.Retail;


public interface DAOInterface{
	public boolean createDatabase(String dbName);
	public boolean executeStatement(String query);
	public boolean insert(Code code);
	public boolean insert(Retail retail);
}
