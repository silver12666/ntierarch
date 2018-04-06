package com.silver.dao;

import com.silver.dao.UserDaoImpl;
import com.silver.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class UserDaoImplTest {

    @Mock private Connection mockConnection;
    @Mock private PreparedStatement mockStatement;
    @Mock private ResultSet mockResultSet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void create() {
    }

    @Test
    public void findById() throws Exception {
        Mockito.when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(mockResultSet.getLong("id")).thenReturn(500L);
        Mockito.when(mockResultSet.getString("name")).thenReturn("David");
        Mockito.when(mockResultSet.getString("email")).thenReturn("david@foo.com");
        Mockito.when(mockResultSet.getString("password")).thenReturn("123456789");
        Mockito.when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        Mockito.when(mockConnection.prepareStatement("SELECT * FROM users WHERE id = ?")).thenReturn(mockStatement);
        Mockito.doNothing().when(mockStatement).setLong(1, 1L);

        UserDaoImpl userDao = new UserDaoImpl(mockConnection);
        User actual = userDao.findById(1L);
        assertEquals(500L, actual.getId().longValue());
        assertEquals("David", actual.getName());
        assertEquals("david@foo.com", actual.getEmail());
        assertEquals("123456789", actual.getPassword());
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}