package org.springframework.github.core.repository;

import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.github.core.model.entity.Issues;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@DataJpaTest
public class IssueRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void saveShouldReturnCorrectly() throws Exception {
        Issues issue = new Issues("first", "second");
        Issues saved =  this.testEntityManager.persistFlushFind(issue);
        BDDAssertions.then(saved.getId()).isNotNull();
        BDDAssertions.then(saved.getUsername()).isEqualToIgnoringCase("first");
        BDDAssertions.then(saved.getRepository()).isEqualToIgnoringCase("second");
    }


}
