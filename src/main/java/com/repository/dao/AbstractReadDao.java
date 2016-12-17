package com.repository.dao;

import com.repository.base.BaseObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by finderlo on 16-12-17.
 */
@Transactional
public abstract class AbstractReadDao<T> extends BaseObject  {

    @Autowired
    protected SessionFactory sessionFactory;

    protected List<String> ids = new ArrayList<>();
    protected String id;

    public List<T> sort(List<T> T) {
        return T;
    }

    public List<T> findAll() {
        Session session = sessionFactory.getCurrentSession();
        List<T> tList = session.createQuery("from " + bindClassName()).list();

        if (tList == null) {
            logger.info("findAll:Type:'" + this.getClass().getSimpleName() + "'" + "find null");
            return new ArrayList<T>();
        }
        return sort(tList);
    }

    public List<T> findBy(String[] keys, String[] values) {
        return findBy(keys, values, true);
    }

    public List<T> findBy(String key, String value) {
        return findBy(new String[]{key}, new String[]{value}, true);
    }

    public List<T> findBy(String key, String value, boolean isLikeQuery) {
        return findBy(new String[]{key}, new String[]{value}, isLikeQuery);
    }

    public List<T> findBy(String[] keys, String[] values, boolean isLikeQuery) {

        if (keys == null || values == null || keys.length == 0 || values.length == 0) {
            return new ArrayList<T>();
        }
        int length = keys.length <= values.length ? keys.length : values.length;
        int enableCount = 0;
        for (int i = 0; i < length; i++) {
            if (!bindKeys().contains(keys[i])) {
                //todo 空值判定
            }
        }

        String hql = jointLikeQuery(keys, values, isLikeQuery);

        if (hql == null) {
            return new ArrayList<T>();
        }

        Session session = sessionFactory.getCurrentSession();

        List<T> result = session.createQuery(hql).list();
        return sort(result);
    }

    public T findById(String id) {

        if (sessionFactory == null) {
            System.out.println("sess:null");
        }
        if (isMoreId()) {
            throw new UnsupportedOperationException();
        }

        if (id == null || id.trim().equals("")) {
            return null;
        }
        HashMap<String, String> idAndValue = new HashMap<>();
        idAndValue.put(getId(), id);
        return findByIds(idAndValue);
    }

    public T findByIds(Map<String, String> idAndValues) {
        Session session = sessionFactory.getCurrentSession();
        String hql = jointHqlByIdsQuery(idAndValues);
        List<T> tList = session.createQuery(hql).list();
        if (tList.isEmpty()) {
            logger.error("tlist is empty");
            return null;
        }
        logger.info(tList.get(0));
        return tList.get(0);
    }

    protected String jointLikeQuery(String[] keys, String[] values, boolean isLikeQuery) {
//        String hql = " from UsersEntity e where e.usersName like 'xiao%' and e.usersPassword like 'psd%'";
        String head = "from " + bindClassName();

        int length = keys.length <= values.length ? keys.length : values.length;
        int enableCount = 0;

        StringBuilder hqlbuilder = new StringBuilder(head);
        boolean isFirst = true;
        for (int i = 0; i < length; i++) {
            if (keys[i] == null || keys[i].trim().equals("") || values[i] == null || values[i].trim().equals("")) {
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
                values[i] = "%" + values[i] + "%";
            } else {
                hqlbuilder.append(" =");
            }
            hqlbuilder.append("'").append(values[i]).append("'");
            enableCount++;
        }
        if (enableCount == 0) {
            return null;
        }
        System.out.println(hqlbuilder.toString());
        return hqlbuilder.toString();
    }

    protected String jointHqlByIdsQuery(Map<String, String> idAndValues) {

        String hql = " from " + bindClassName() + " e where ";
        StringBuilder hqlbuilder = new StringBuilder();
        boolean isFirst = true;
        for (Map.Entry<String, String> entry : idAndValues.entrySet()) {
            String value = entry.getValue().trim();
            String key = entry.getKey().trim();
            if (!isFirst) {
                hqlbuilder.append(" and");
            } else {
                isFirst = false;
            }
            hqlbuilder.append(" e.").append(key).append("=").append("'").append(value).append("'");
        }
        return hql + hqlbuilder.toString();
    }

    protected String bindClassName() {
        return bindClass().getSimpleName();
    }

    protected List<String> getIds() {
        if (ids.isEmpty()) {
            findIds();
        }
        return ids;
    }

    protected String getId() {
        if (id == null) {
            findIds();
        }
        return id;
    }

    protected void findIds() {
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

    protected List<String> bindKeys() {
        List<String> results = new ArrayList<>();
        for (Field field : bindClass().getDeclaredFields()) {
            results.add(field.getName());
        }
        return results;
    }

    protected boolean isMoreId() {
        if (getIds().size() > 1) {
            return true;
        } else {
            return false;
        }
    }

    protected Class bindClass() {
        Type sType = getClass().getGenericSuperclass();
        Type[] generics = ((ParameterizedType) sType).getActualTypeArguments();
        Class<T> mTClass = (Class<T>) (generics[0]);
        return mTClass;
    }

}
