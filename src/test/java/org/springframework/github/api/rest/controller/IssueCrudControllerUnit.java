package org.springframework.github.api.rest.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.github.TestUtil;
import org.springframework.github.core.model.entity.Issues;
import org.springframework.github.core.service.exception.BadRequestException;
import org.springframework.github.core.service.exception.ConflictException;
import org.springframework.github.core.service.exception.NotFoundException;
import org.springframework.github.core.service.impl.IssuesCrudService;
import org.springframework.github.webservice.api.rest.controller.IssuesCrudController;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class IssueCrudControllerUnit {

    @Mock
    private IssuesCrudService mockSvc;

    @InjectMocks
    private IssuesCrudController testObj;

    @Test
    public void getAllTest() throws Exception {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());
        List<Issues> resourceList = new ArrayList<Issues>();
        resourceList.add(resource);


        // Define mock object behavior:
        when(mockSvc.getAll()).thenReturn(resourceList);

        // Define expected results:
        List<Issues> expectedResult = resourceList;

        // Call test method:
        List<Issues> actualResult = testObj.getAll().getBody();

        // Validate dependencies were called:
        verify(mockSvc, times(1)).getAll();

        // Validate results:
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void getOneTest() throws Exception {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());
        List<Issues> resourceList = new ArrayList<Issues>();
        resourceList.add(resource);

        // Define mock object behavior:
        when(mockSvc.getOne(anyLong())).thenReturn(resource);

        // Define expected results:
        Issues expectedResult = resource;

        // Call test method:
        Issues actualResult = testObj.getOne(TestUtil.dummyLong()).getBody();

        // Validate dependencies were called:
        verify(mockSvc, times(1)).getOne(anyLong());

        // Validate results:
        assertEquals(expectedResult, actualResult);

    }

    @Test(expected=NotFoundException.class)
    public void getOneNotFoundExceptionTest() throws Exception {

        // Define mock object behavior:
        doThrow(new NotFoundException(IssuesCrudService.NotFoundMessage))
                .when(mockSvc).getOne(anyLong());

        // Call test method:
        testObj.getOne(TestUtil.dummyLong());
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
        Issues expectedResult = resource;

        // Call test method:
        Issues actualResult = testObj.post(resource).getBody();

        // Validate dependencies were called:
        verify(mockSvc, times(1)).create(any(Issues.class));

        // Validate results:
        assertEquals(expectedResult, actualResult);

    }

    @Test(expected=ConflictException.class)
    public void postConflictExceptionTest() throws Exception {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());

        // Define mock object behavior:
        doThrow(new ConflictException(IssuesCrudService.ConflictMessage)).when(mockSvc).create(any(Issues.class));

        // Call test method:
        testObj.post(resource);

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
        Issues expectedResult = resource;

        // Call test method:
        Issues actualResult = testObj.put(resource.getId(), resource).getBody();

        // Validate dependencies were called:
        verify(mockSvc, times(1)).update(anyLong(), any(Issues.class));

        // Validate results:
        assertEquals(expectedResult, actualResult);

    }

    @Test(expected=BadRequestException.class)
    public void putBadRequestExceptionTest() throws Exception {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());

        // Define mock object behavior:
        doThrow(new BadRequestException(IssuesCrudService.BadRequestMessage))
                .when(mockSvc).update(anyLong(), any(Issues.class));

        // Call test method:
        testObj.put(TestUtil.dummyLong(), resource);

    }

    @Test(expected=NotFoundException.class)
    public void putNotFoundExceptionTest() throws Exception {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());

        // Define mock object behavior:
        doThrow(new NotFoundException(IssuesCrudService.NotFoundMessage))
                .when(mockSvc).update(anyLong(), any(Issues.class));

        // Call test method:
        testObj.put(TestUtil.dummyLong(), resource);
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
        Issues expectedResult = resource;

        // Call test method:
        Issues actualResult = testObj.delete(resource.getId()).getBody();

        // Validate dependencies were called:
        verify(mockSvc, times(1)).delete(anyLong());

        // Validate results:
        assertEquals(expectedResult, actualResult);


    }

    @Test(expected=NotFoundException.class)
    public void deleteNotFoundExceptionTest() throws Exception {

        // Define mock object behavior:
        doThrow(new NotFoundException(IssuesCrudService.NotFoundMessage))
                .when(mockSvc).delete(anyLong());

        // Call test method:
        testObj.delete(TestUtil.dummyLong());

    }


}
