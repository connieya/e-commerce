package com.example.hanghaeplus.common;

import com.example.hanghaeplus.common.builder.BuilderSupporter;
import com.example.hanghaeplus.common.builder.TestFixtureBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
//@Import(value = {TestFixtureBuilder.class, BuilderSupporter.class})
public abstract class RepositoryTest {

    @Autowired
    protected TestFixtureBuilder testFixtureBuilder;
}
