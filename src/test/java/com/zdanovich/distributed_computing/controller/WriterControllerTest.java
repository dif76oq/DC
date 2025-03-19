package com.zdanovich.distributed_computing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdanovich.distributed_computing.dto.request.WriterRequestTo;
import com.zdanovich.distributed_computing.dto.response.WriterResponseTo;
import com.zdanovich.distributed_computing.exception.DuplicateFieldException;
import com.zdanovich.distributed_computing.exception.EntityNotFoundException;
import com.zdanovich.distributed_computing.service.WriterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WriterControllerTest {

    @Mock
    private WriterService writerService;

    @InjectMocks
    private WriterController writerController;

    private MockMvc mockMvc;

    private WriterRequestTo writerRequestTo;

    private WriterResponseTo writerResponseTo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(writerController).build();

        // Prepare test data
        writerRequestTo = new WriterRequestTo();
        writerRequestTo.setId(1L);
        writerRequestTo.setLogin("testLogin");
        writerRequestTo.setPassword("testPassword123");
        writerRequestTo.setFirstname("Test");
        writerRequestTo.setLastname("Writer");

        writerResponseTo = new WriterResponseTo();
        writerResponseTo.setId(1L);
        writerResponseTo.setLogin("testLogin");
        writerResponseTo.setFirstname("Test");
        writerResponseTo.setLastname("Writer");
    }

    @Test
    void findAll() throws Exception {
        when(writerService.findAll()).thenReturn(Arrays.asList(writerResponseTo));

        mockMvc.perform(get("/api/v1.0/writers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].login").value("testLogin"))
                .andExpect(jsonPath("$[0].firstname").value("Test"))
                .andExpect(jsonPath("$[0].lastname").value("Writer"));
    }

    @Test
    void findById_Success() throws Exception {
        when(writerService.findById(1L)).thenReturn(writerResponseTo);

        mockMvc.perform(get("/api/v1.0/writers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.login").value("testLogin"));
    }

    @Test
    void findById_NotFound() throws Exception {
        when(writerService.findById(1L)).thenThrow(new EntityNotFoundException("Writer not found"));

        mockMvc.perform(get("/api/v1.0/writers/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Writer not found"));
    }

    @Test
    void save_Success() throws Exception {
        when(writerService.save(any(WriterRequestTo.class))).thenReturn(writerResponseTo);

        mockMvc.perform(post("/api/v1.0/writers")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(writerRequestTo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.login").value("testLogin"));
    }

    @Test
    void save_Conflict() throws Exception {
        when(writerService.save(any(WriterRequestTo.class))).thenThrow(new DuplicateFieldException("login", "login"));

        mockMvc.perform(post("/api/v1.0/writers")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(writerRequestTo)))
                .andExpect(status().isConflict());
                //.andExpect(content().string("Login already exists"));
    }

    @Test
    void update_Success() throws Exception {
        when(writerService.update(any(WriterRequestTo.class))).thenReturn(writerResponseTo);

        mockMvc.perform(put("/api/v1.0/writers")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(writerRequestTo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.login").value("testLogin"));
    }

    @Test
    void update_NotFound() throws Exception {
        when(writerService.update(any(WriterRequestTo.class))).thenThrow(new EntityNotFoundException("Writer not found"));

        mockMvc.perform(put("/api/v1.0/writers")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(writerRequestTo)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Writer not found"));
    }

    @Test
    void delete_Success() throws Exception {
        doNothing().when(writerService).delete(1L);

        mockMvc.perform(delete("/api/v1.0/writers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_NotFound() throws Exception {
        doThrow(new EntityNotFoundException("Writer not found")).when(writerService).delete(1L);

        mockMvc.perform(delete("/api/v1.0/writers/1"))
                .andExpect(status().isNotFound());
                //.andExpect(content().string("Writer not found"));
    }

    @Test
    void findByIssueId_Success() throws Exception {
        when(writerService.findByIssueId(1L)).thenReturn(writerResponseTo);

        mockMvc.perform(get("/api/v1.0/writers/by-issue/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.login").value("testLogin"));
    }

    @Test
    void findByIssueId_NotFound() throws Exception {
        when(writerService.findByIssueId(1L)).thenThrow(new EntityNotFoundException("Writer not found"));

        mockMvc.perform(get("/api/v1.0/writers/by-issue/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Writer not found"));
    }
}
