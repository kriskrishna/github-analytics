package org.springframework.github.core.service.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.github.TestUtil;
import org.springframework.github.core.model.entity.Issues;
import org.springframework.github.core.repository.IssuesRepository;
import org.springframework.github.core.service.exception.BadRequestException;
import org.springframework.github.core.service.exception.ConflictException;
import org.springframework.github.core.service.exception.NotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class IssuesCrudServiceUnit {

    @Mock
    private IssuesRepository mockRepo;

    @InjectMocks
    private IssuesCrudService testObj;

    @Test
    public void getAllTest() {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());
        List<Issues> resourceList = new ArrayList<Issues>();
        resourceList.add(resource);

        // Define mock object behavior:
        when(mockRepo.findAll()).thenReturn(resourceList);

        // Define expected results:
        List<Issues> expectedResult = resourceList;

        // Call test method:
        List<Issues> actualResult = testObj.getAll();

        // Validate dependencies were called
        verify(mockRepo, times(1)).findAll();

        // Validate results:
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void getOneTest() throws NotFoundException {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());

        // Define mock object behavior:
        when(mockRepo.findOne(anyLong())).thenReturn(resource);

        // Define expected results:
        Issues expectedResult = resource;

        // Call test method:
        Issues actualResult = testObj.getOne(resource.getId());

        // Validate dependencies were called
        verify(mockRepo, times(1)).findOne(anyLong());

        // Validate results:
        assertEquals(expectedResult, actualResult);

    }

    @Test(expected=NotFoundException.class)
    public void getOneNotFoundExceptionTest() throws NotFoundException {

        // Define mock object behavior:
        when(mockRepo.findOne(anyLong())).thenReturn(null);

        // Call test method:
        testObj.getOne(TestUtil.dummyLong());

    }

    @Test
    @Ignore
    public void createTest() throws ConflictException {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());

        // Define mock object behavior:
        when(mockRepo.saveAndFlush(any(Issues.class))).thenReturn(resource);

        // Define expected results:
        Issues expectedResult = resource;

        // Call test method:
        Issues actualResult = testObj.create(resource);

        // Validate dependencies were called
        verify(mockRepo, times(1)).saveAndFlush(any(Issues.class));

        // Validate results:
        assertEquals(expectedResult, actualResult);

    }

    @Test(expected=ConflictException.class)
    public void createConflictExceptionTest() throws ConflictException {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());

        // Call test method:
        testObj.create(resource);

    }

    @Test
    public void updateTest() throws NotFoundException, BadRequestException {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());

        // Define mock object behavior:
        when(mockRepo.exists(anyLong())).thenReturn(true);
        when(mockRepo.saveAndFlush(any(Issues.class))).thenReturn(resource);

        // Define expected results:
        Issues expectedResult = resource;

        // Call test method:
        Issues actualResult = testObj.update(resource.getId(), resource);

        // Validate dependencies were called
        verify(mockRepo, times(1)).exists(anyLong());
        verify(mockRepo, times(1)).saveAndFlush(any(Issues.class));

        // Validate results:
        assertEquals(expectedResult, actualResult);

    }

    @Test(expected=NotFoundException.class)
    public void updateNotFoundExceptionTest() throws NotFoundException, BadRequestException {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());

        // Define mock object behavior:
        when(mockRepo.exists(anyLong())).thenReturn(false);

        // Call test method:
        testObj.update(resource.getId(), resource);

    }

    @Test(expected=BadRequestException.class)
    public void updateBadRequestExceptionTest() throws NotFoundException, BadRequestException {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());

        // Define mock object behavior:
        when(mockRepo.exists(anyLong())).thenReturn(true);

        // Call test method:
        testObj.update(TestUtil.dummyLong(), resource);

    }

    @Test
    public void deleteTest() throws NotFoundException {

        // Define dummy data:
        Issues resource = new Issues();
        resource.setId(TestUtil.dummyLong());
        resource.setUsername(TestUtil.dummyString());
        resource.setRepository(TestUtil.dummyString());

        // Define mock object behavior:
        when(mockRepo.findOne(anyLong())).thenReturn(resource);

        // Define expected results:
        Issues expectedResult = resource;

        // Call test method:
        Issues actualResult = testObj.delete(resource.getId());

        // Validate dependencies were called
        verify(mockRepo, times(1)).findOne(anyLong());
        verify(mockRepo, times(1)).delete(anyLong());

        // Validate results:
        assertEquals(expectedResult, actualResult);

    }

    @Test(expected=NotFoundException.class)
    public void deleteNotFoundExceptionTest() throws NotFoundException {

        // Define mock object behavior:
        when(mockRepo.findOne(anyLong())).thenReturn(null);

        // Call test method:
        testObj.delete(TestUtil.dummyLong());

    }

}
