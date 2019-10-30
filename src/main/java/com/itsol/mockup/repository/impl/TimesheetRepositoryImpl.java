package com.itsol.mockup.repository.impl;

import com.itsol.mockup.repository.TimesheetRepositoryCustom;
import com.itsol.mockup.utils.HibernateUtil;
import com.itsol.mockup.web.dto.request.ReportDTO;
import com.itsol.mockup.web.dto.response.BaseResultDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Repository
public class TimesheetRepositoryImpl implements TimesheetRepositoryCustom {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public ReportDTO getToTalMemberNotHaveTimeSheet(Long projectId, String date) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
/*            NativeQuery<BigInteger> query =
                    session.createNativeQuery("select count (*) from TIMESHEET t where t.PROJECT_ID = :p_id and trunc(t.CREATED_DATE) = to_date('26-10-2019', 'dd-mm-yyyy')");*/
            /*query.setParameter("p_date",1);
            query.setParameter("p_id",26-10-2019);*/
           /* query.addScalar("totalTimesheet", new IntegerType());
            query.setResultTransformer(Transformers.aliasToBean(ReportDTO.class));*/
          /* long totalRecord = query.getSingleResult().longValue();*/
        }catch (Exception e){
            e.getMessage();
        }finally {
            session.close();
        }
        return null;
    }
}
