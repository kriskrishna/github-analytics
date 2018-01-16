package org.springframework.github.api.rest.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.github.ComponentTestBase;
import org.springframework.github.TestUtil;
import org.springframework.github.core.model.entity.Issues;
import org.springframework.github.core.service.exception.BadRequestException;
import org.springframework.github.core.service.exception.ConflictException;
import org.springframework.github.core.service.exception.NotFoundException;
import org.springframework.github.core.service.impl.IssuesCrudService;
import org.springframework.github.webservice.api.rest.controller.IssuesCrudController;
import org.springframework.github.webservice.api.rest.exception.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/snippets")
@AutoConfigureJsonTesters
public class IssueCrudControllerComponent extends ComponentTestBase {

    @MockBean(name = "issuesCrudService")
    private IssuesCrudService mockSvc;

    @InjectMocks
    private IssuesCrudController testObj;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void before() {

        // Configure context to use mock dependencies:
        MockitoAnnotations.initMocks(this);

    };

    @Test
    public void getAllTest() throws Exception {

        // Define dummy data:
        Issues resource = new Issues();
        Long id = TestUtil.dummyLong();
        resource.setId(id);
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());
        List<Issues> resourceList = new ArrayList<Issues>();
        resourceList.add(resource);

        // Define mock object behavior:
        when(mockSvc.getAll()).thenReturn(resourceList);

        // Define expected results:
        String expectedResult = serializeJSON(resourceList);

        // Call test method:
        ResultActions actualResult = mockMvc.perform(
                get(IssuesCrudController.PATH)
                        .accept(MediaType.APPLICATION_JSON)
        );


        // Validate dependencies were called:
        verify(mockSvc, times(1)).getAll();

        // Validate results:
        actualResult
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(expectedResult))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("@.[0].id").value(id))
                .andDo(MockMvcRestDocumentation.document("issues"));
    }

    @Test
    public void getOneTest() throws Exception {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());

        // Define mock object behavior:
        when(mockSvc.getOne(anyLong())).thenReturn(resource);

        // Define expected results:
        String expectedResult = serializeJSON(resource);

        // Call test method:
        ResultActions actualResult = mockMvc.perform(
                get(IssuesCrudController.PATH + "/" + resource.getId())
                        .accept(MediaType.APPLICATION_JSON)
        );

        // Validate dependencies were called:
        verify(mockSvc, times(1)).getOne(anyLong());

        // Validate results:
        actualResult
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(expectedResult));

    }

    @Test
    public void getOneNotFoundExceptionTest() throws Exception {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());

        // Define mock object behavior:
        doThrow(new NotFoundException(IssuesCrudService.NotFoundMessage))
                .when(mockSvc).getOne(anyLong());

        // Define expected results:
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, IssuesCrudService.NotFoundMessage);
        String expectedResult = serializeJSON(apiError);

        // Call test method:
        ResultActions actualResult = mockMvc.perform(
                get(IssuesCrudController.PATH + "/" + resource.getId())
                        .accept(MediaType.APPLICATION_JSON)
        );

        // Validate dependencies were called:
        verify(mockSvc, times(1)).getOne(anyLong());

        // Validate results:
        actualResult
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(expectedResult));

    }

    @Test
    public void postTest() throws Exception {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());

        // Define mock object behavior:
        when(mockSvc.create(any(Issues.class))).thenReturn(resource);

        // Define expected results:
        String expectedResult = serializeJSON(resource);

        // Call test method:
        ResultActions actualResult = mockMvc.perform(
                post(IssuesCrudController.PATH)
                        .content(serializeJSON(resource))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // Validate dependencies were called:
        verify(mockSvc, times(1)).create(any(Issues.class));

        // Validate results:
        actualResult
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(expectedResult));

    }

    @Test
    public void postConflictExceptionTest() throws Exception {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());

        // Define mock object behavior:
        doThrow(new ConflictException(IssuesCrudService.ConflictMessage)).when(mockSvc).create(any(Issues.class));

        // Define expected results:
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, IssuesCrudService.ConflictMessage);
        String expectedResult = serializeJSON(apiError);

        // Call test method:
        ResultActions actualResult = mockMvc.perform(
                post(IssuesCrudController.PATH)
                        .content(serializeJSON(resource))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // Validate dependencies were called:
        verify(mockSvc, times(1)).create(any(Issues.class));

        // Validate results:
        actualResult
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(expectedResult));

    }

    @Test
    public void putTest() throws Exception {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());

        // Define mock object behavior:
        when(mockSvc.update(anyLong(), any(Issues.class))).thenReturn(resource);

        // Define expected results:
        String expectedResult = serializeJSON(resource);

        // Call test method:
        ResultActions actualResult = mockMvc.perform(
                put(IssuesCrudController.PATH + "/" + TestUtil.dummyLong())
                        .content(serializeJSON(resource))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // Validate dependencies were called:
        verify(mockSvc, times(1)).update(anyLong(), any(Issues.class));

        // Validate results:
        actualResult
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(expectedResult));

    }

    @Test
    public void putBadRequestExceptionTest() throws Exception {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());

        // Define mock object behavior:
        doThrow(new BadRequestException(IssuesCrudService.BadRequestMessage))
                .when(mockSvc).update(anyLong(), any(Issues.class));

        // Define expected results:
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, IssuesCrudService.BadRequestMessage);
        String expectedResult = serializeJSON(apiError);

        // Call test method:
        ResultActions actualResult = mockMvc.perform(
                put(IssuesCrudController.PATH + "/" + TestUtil.dummyLong())
                        .content(serializeJSON(resource))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // Validate dependencies were called:
        verify(mockSvc, times(1)).update(anyLong(), any(Issues.class));

        // Validate results:
        actualResult
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(expectedResult));

    }

    @Test
    public void putNotFoundExceptionTest() throws Exception {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());

        // Define mock object behavior:
        doThrow(new NotFoundException(IssuesCrudService.NotFoundMessage))
                .when(mockSvc).update(anyLong(), any(Issues.class));

        // Define expected results:
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, IssuesCrudService.NotFoundMessage);
        String expectedResult = serializeJSON(apiError);

        // Call test method:
        ResultActions actualResult = mockMvc.perform(
                put(IssuesCrudController.PATH + "/" + TestUtil.dummyLong())
                        .content(serializeJSON(resource))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // Validate dependencies were called:
        verify(mockSvc, times(1)).update(anyLong(), any(Issues.class));

        // Validate results:
        actualResult
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(expectedResult));

    }

    @Test
    public void deleteTest() throws Exception {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());

        // Define mock object behavior:
        when(mockSvc.delete(anyLong())).thenReturn(resource);

        // Define expected results:
        String expectedResult = serializeJSON(resource);

        // Call test method:
        ResultActions actualResult = mockMvc.perform(
                delete(IssuesCrudController.PATH + "/" + TestUtil.dummyLong())
                        .accept(MediaType.APPLICATION_JSON)
        );

        // Validate dependencies were called:
        verify(mockSvc, times(1)).delete(anyLong());

        // Validate results:
        actualResult
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(expectedResult));

    }

    @Test
    public void deleteNotFoundExceptionTest() throws Exception {

        // Define mock object behavior:
        doThrow(new NotFoundException(IssuesCrudService.NotFoundMessage))
                .when(mockSvc).delete(anyLong());

        // Define expected results:
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, IssuesCrudService.NotFoundMessage);
        String expectedResult = serializeJSON(apiError);

        // Call test method:
        ResultActions actualResult = mockMvc.perform(
                delete(IssuesCrudController.PATH + "/" + TestUtil.dummyLong())
                        .accept(MediaType.APPLICATION_JSON)
        );

        // Validate dependencies were called:
        verify(mockSvc, times(1)).delete(anyLong());

        // Validate results:
        actualResult
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(expectedResult));

    }
}
