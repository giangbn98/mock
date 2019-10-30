package com.itsol.mockup.repository.impl;

import com.itsol.mockup.entity.PermissionEntity;
import com.itsol.mockup.repository.PermissionRepositoryCustom;
import com.itsol.mockup.utils.HibernateUtil;
import com.itsol.mockup.web.dto.issue.IssueDTO;
import com.itsol.mockup.web.dto.permisson.PermissionDTO;
import com.itsol.mockup.web.dto.users.UsersDTO;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class PermissionRepositoryImpl implements PermissionRepositoryCustom {
    @Override
    public PermissionEntity findPermissionByUserId(Long userId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
        }catch (Exception e){

        }
        return null;
    }
}
