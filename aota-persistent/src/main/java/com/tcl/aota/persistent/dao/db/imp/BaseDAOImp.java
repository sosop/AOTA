package com.tcl.aota.persistent.dao.db.imp;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.perf4j.aop.Profiled;

import com.tcl.aota.common.constants.SQL;
import com.tcl.aota.common.utils.StringUtils;
import com.tcl.aota.persistent.dao.db.BaseDAO;

public abstract class BaseDAOImp<T, M> implements BaseDAO<T> {
	@Resource(name = "sqlSession")
	protected SqlSessionTemplate sqlSession;
	
	private String baseMapper;
	
	public BaseDAOImp() {
		Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments();
		baseMapper = types[1].toString().substring(10);
	}

	@Override
	@Profiled()
	public int insert(T obj) {
		String sqlMap = StringUtils.append(baseMapper, ".", SQL.SQL_ID_INSERT);
		return sqlSession.insert(sqlMap, obj);
	}

	@Override
	@Profiled
	public int update(T obj) {
		String sqlMap = StringUtils.append(baseMapper, ".", SQL.SQL_ID_UPDATE);
		return sqlSession.update(sqlMap, obj);
	}

	@Override
	@Profiled
	public int delete(T obj) {
		String sqlMap = StringUtils.append(baseMapper, ".", SQL.SQL_ID_DELETE);
		return sqlSession.delete(sqlMap, obj);
	}

	@Override
	@Profiled
	public int delete(int id) {
		String sqlMap = StringUtils.append(baseMapper, ".", SQL.SQL_ID_DELETE);
		return sqlSession.delete(sqlMap, id);
	}

	@Override
	@Profiled
	public T selectByCondition(Object condition) {
		String sqlMap = StringUtils.append(baseMapper, ".", SQL.SQL_ID_SELECT_BY_CONDITION);
		return sqlSession.selectOne(sqlMap, condition);
	}

	@Override
	@Profiled
	public List<T> selectMulByCondition(Object condition) {
		String sqlMap = StringUtils.append(baseMapper, ".", SQL.SQL_ID_SELECT_MUL_BY_CONDITION);
		return sqlSession.selectList(sqlMap, condition);
	}

	@Override
	@Profiled
	public List<T> select() {
		String sqlMap = StringUtils.append(baseMapper, ".", SQL.SQL_ID_SELECT);
		return sqlSession.selectList(sqlMap);
	}

	@Override
	@Profiled
	public long count(String... condition) {
		long total = 0L;
		if (StringUtils.isNull(condition)) {
			total = (Long)sqlSession.selectOne(StringUtils.append(baseMapper, ".", SQL.SQL_ID_COUNT));
		} else {
			total = (Long)sqlSession.selectOne(StringUtils.append(baseMapper, ".", SQL.SQL_ID_COUNT_CONDITION, condition));
		}
		return total;
	}
}
