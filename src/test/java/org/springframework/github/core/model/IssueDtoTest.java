package org.springframework.github.core.model;

import org.assertj.core.api.BDDAssertions;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertThat;

public class IssueDtoTest {

    private Validator validator;
    @Before
    public void before() throws Throwable {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean.getValidator();
    }

    @Test
    public void newInstanceWithVaidValuesShouldReturnARecord(){

        IssueDto issueDto = new IssueDto("first", "second", null);
        Assert.assertEquals("Issues should have username", issueDto.getUserName(), "first");
        assertThat(issueDto.getUserName(), Matchers.is("first"));

        BDDAssertions.then(issueDto.getUserName()).isEqualToIgnoringCase("first");
        BDDAssertions.then(issueDto.getRepository()).isEqualToIgnoringCase("second");
    }

    @Test
    public void newInstanceWithInvalidConstraintsShouldProduceonstraintViolations(){
        IssueDto issueDto = new IssueDto(null, null, null);
        Set<ConstraintViolation<IssueDto>> constraintViolations = validator.validate(issueDto);
        BDDAssertions.then(constraintViolations.size()).isEqualTo(2);

    }

}
