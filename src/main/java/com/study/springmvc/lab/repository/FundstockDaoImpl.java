package com.study.springmvc.lab.repository;

import java.sql.ResultSet;
import java.util.List;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.study.springmvc.lab.entity.Fund;
import com.study.springmvc.lab.entity.Fundstock;

@Repository
public class FundstockDaoImpl implements FundstockDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//	【查詢】
	@Override
	public List<Fundstock> queryAll() {
		return queryAllCase3();
	}
	
	// -- 查詢的三種方式：
		private List<Fundstock> queryAllCase1() {
			String sql = " select s.sid, s.fid, s.symbol, s.share from fundstock s order by s.sid";
			return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Fundstock>(Fundstock.class));
		}
		
		private List<Fundstock> queryAllCase2() {
			String sql = " select s.sid, s.fid, s.symbol, s.share from fundstock s order by s.sid";
			RowMapper<Fundstock> rm = (ResultSet rs, int rowNum) -> {
				Fundstock fundstock = new Fundstock();
				fundstock.setSid(rs.getInt("sid"));
				fundstock.setFid(rs.getInt("fid"));
				fundstock.setSymbol(rs.getString("symbol"));
				fundstock.setShare(rs.getInt("share"));
				// 根據 fid 查詢 fund 列表
				String sql2 = "sselect f.fid, f.fname, f.createtime "
						+ "from fund f "
						+ "where f.fid = ? "
						+ "order by f.fid";
				Object[] args = {fundstock.getFid()};
				Fund funds = jdbcTemplate.query(
						sql2,
						args,
						(ResultSetExtractor<Fund>) new Fund());
				fundstock.setFund(funds);
				return fundstock;
			};
			return jdbcTemplate.query(sql, rm);
		}
		
		private List<Fundstock> queryAllCase3() {
			// fundstocks_sid 其中是 fundstocks 指的是 Fund.java 一對多的屬性命名，不是tablec名
			String sql = "select s.sid , s.fid , s.symbol, s.share ,"
					+ "f.fid AS fund_fid, "
					+ "f.fname AS fund_fname, "
					+ "f.createtime AS fund_createtime "
					+ "from fundstock s left join fund f "
					+ "on s.fid = f.fid order by s.sid ;";
			ResultSetExtractor<List<Fundstock>> resultSetExtractor = 
					JdbcTemplateMapperFactory.newInstance()
					.addKeys("sid") // Fund 主鍵
					.newResultSetExtractor(Fundstock.class);
			return jdbcTemplate.query(sql, resultSetExtractor);
		}
	// -- 查詢的三種方式。

	//	【功能】分頁顯示基金 (5筆/頁)
	@Override
	public List<Fundstock> queryPage(int offset) {
		if(offset < 0) {
			return queryAll();
		}
		String sql = "select s.sid, s.fid, s.symbol, s.share , "
				+ "f.fid as fund_fid , f.fname as fund_fname , f.createtime as fund_createtime  "
				+ "from fundstock s left join fund f " + "on f.fid = s.fid order by s.sid ";
		sql += String.format(" limit %d offset %d ", FundstockDao.LIMIT, offset);
		
		ResultSetExtractor<List<Fundstock>> resultSetExtractor = 
				JdbcTemplateMapperFactory.newInstance()
				.addKeys("sid") // 主鍵
				.newResultSetExtractor(Fundstock.class);

		return jdbcTemplate.query(sql, resultSetExtractor);
	}

	// 【功能】基金總筆數
	@Override
	public int count() {
		String sql = "select count(*) from fundstock;";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	//  【功能】獲取一筆資料 by sid
	@Override
	public Fundstock get(Integer sid) {
		// 先找到 fundstock
		String sql = "select s.sid , s.fid , s.symbol, s.share "
				+ "from fundstock s "
				+ "where s.sid = ?";
		Fundstock fundstock = jdbcTemplate.queryForObject(sql,
				new BeanPropertyRowMapper<Fundstock>(Fundstock.class), sid);
		// 再透過 fundstock.getFid() 找到 fund
		sql = "select f.fid, f.fname, f.createtime from fund f where f.fid = ?;";
		Fund fund = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Fund>(Fund.class),
				fundstock.getFid());
		// 注入 fund
		fundstock.setFund(fund);
		return fundstock;
	}
	
	//	【新增】
	@Override
	public int add(Fundstock fundstock) {
		String sql = "insert into fundstock (fid, symbol, share) values (?, ?, ?);";
		int rowcount = jdbcTemplate.update(sql, fundstock.getFid(), fundstock.getSymbol(), fundstock.getShare());
		return rowcount;
	}
	
	//	【修改】
	@Override
	public int update(Fundstock fundstock) {
		String sql = "update fundstock set fid=?, symbol=?, share=?;";
		int rowcount = jdbcTemplate.update(sql, fundstock.getFid(), fundstock.getSymbol(), fundstock.getShare());
		return rowcount;
	}

	//	【刪除】
	@Override
	public int delete(Integer sid) {
		String sql = "delete from funstock where sid = ?";
		int rowcount = jdbcTemplate.update(sql, sid);
		return rowcount;
	}

	
}
