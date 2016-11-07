package com.repertory.dao;

import com.repertory.config.Factory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Finderlo on 2016/11/4.
 */
public abstract class AbstractDao<T extends Object> {

//    @Inject
    @Autowired
    protected SessionFactory sessionFactory = Factory.sessionFactory();

    private List<String> ids = new ArrayList<>();
    private String id;

    public T queryById(String parm) {
        if (isMoreId()){
            throw new UnsupportedOperationException();
        }
        HashMap<String, String> idAndValue = new HashMap<>();
        idAndValue.put(getId(), parm);
        return queryByIds(idAndValue);
    }

    public T queryByIds(Map<String, String> idAndValues) {
        Session session = sessionFactory.openSession();
        String hql = jointHqlByIdsQuery(idAndValues);
        Query query = session.createQuery(hql);
        List<T> tList = query.list();
        session.close();
        if (tList.isEmpty()) {
            return null;
        }
        return tList.get(0);
    }

    public List<T> query( String[] keys,  String[] values){
        return query(keys, values,true);
    }

    public List<T> query( String[] keys, String[] values, boolean isLikeQuery) {
        Session session = sessionFactory.openSession();
        String hql = jointLikeQuery(keys, values, isLikeQuery);
        Query<T> tQuery = session.createQuery(hql);
        List<T> result = tQuery.list();
        System.out.println(tQuery.getQueryString());
        session.close();
        return result;
    }

    public void save(T t) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(T t){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(t);
        session.getTransaction().commit();
        session.close();
    }

    public void update(T t) {
        if (t == null) {
            return;
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
    }

    public Class bindClass(){
        Type sType = getClass().getGenericSuperclass();
        Type[] generics = ((ParameterizedType) sType).getActualTypeArguments();
        Class<T> mTClass = (Class<T>) (generics[0]);
        return mTClass;
    }

    private String jointLikeQuery( String[] keys, String[] values, boolean isLikeQuery) {
//        String hql = " from UsersEntity e where e.usersName like 'xiao%' and e.usersPassword like 'psd%'";
        String head = "from " + bindClassName();

        if (keys==null || values==null){
            return head;
        }

        StringBuilder hqlbuilder = new StringBuilder();
        boolean isFirst = true;
        for (int i = 0; i < keys.length; i++) {
            if (keys[i]==null || keys[i].trim().equals("") || values[i] == null || values[i].trim().equals("")){
                continue;
            }
            if (!isFirst) {
                hqlbuilder.append(" and ");
            } else {
                hqlbuilder.append(" e where ");
                isFirst = false;
            }
            hqlbuilder.append("e.");
            hqlbuilder.append(keys[i]);
            if (isLikeQuery) {
                hqlbuilder.append(" like ");
                values[i] = "%"+values[i]+"%";
            } else {
                hqlbuilder.append(" =");
            }
            hqlbuilder.append("'").append(values[i]).append("'");
        }
        return head + hqlbuilder.toString();
    }

    private String jointHqlByIdsQuery(Map<String, String> idAndValues) {

        String hql = " from " + bindClassName() + " e where ";
        StringBuilder hqlbuilder = new StringBuilder();
        boolean isFirst = true;
        for (Map.Entry<String, String> entry : idAndValues.entrySet()) {
            String value = entry.getValue();
            String key = entry.getKey();
            if (!isFirst) {
                hqlbuilder.append(" and");
            } else {
                isFirst = false;
            }
            hqlbuilder.append(" e.").append(key).append("=").append("'").append(value).append("'");
        }
        return hql + hqlbuilder.toString();
    }

    private String bindClassName() {
        return bindClass().getSimpleName();
    }

    private List<String> getIds() {
        if (ids.isEmpty()) {
            findIds();
        }
        return ids;
    }

    private String getId(){
        if (id == null){
            findIds();
        }
        return id;
    }

    private void findIds() {
        ids.clear();
        Method[] methods = bindClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(javax.persistence.Id.class)) {
                String methodName = method.getName();
                String name = methodName.substring(3);
                char[] idchar = name.toCharArray();
                String first = String.valueOf(idchar[0]).toLowerCase();
                String id = first + String.copyValueOf(idchar, 1, idchar.length - 1);
                ids.add(id);
            }
        }
        if (ids.size() == 1) {
            id = ids.get(0);
        }
    }

    private boolean isMoreId(){
        if (getIds().size()>1){
            return true;
        }else {
            return false;
        }
    }


}
