package com.study.springmvc.case02.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class LottoService {
	private static List<Set<Integer>> lottos = new ArrayList<>();
	private Map<Integer, Integer> amount = new HashMap<Integer, Integer>();
	private static List<Entry<Integer, Integer>> numbers = new ArrayList<>();
	
//	查詢、統計
	
	public List<Set<Integer>> getLottos() {
		return lottos;
	}
	
	public List<Entry<Integer, Integer>> showLottoNumbers (){
		statisticsLottoNum();
		sortNumbers(amount);
		return numbers;
	}
	
//	新增、修改、刪除
	
	public void addLotto() {
		lottos.add(0, generateLotto());
	}
	
	public void updateLotto(int index) {
		lottos.set(index, generateLotto());
	}
	
	public void deleteLotto(int index) {
		lottos.remove(index);
	}
	
	private Set<Integer> generateLotto(){
		Random r = new Random();
		// 樂透 539 : 1-39 取出不重複的5個號碼
		Set<Integer> lotto = new LinkedHashSet<Integer>();
		while(lotto.size() < 5) {
			lotto.add(r.nextInt(39) + 1);
		}
		return lotto;
	}
	
	private void statisticsLottoNum() {
		amount.clear();
		lottos.stream()
		.flatMap(lottoStr -> lottoStr.stream())
		.distinct()
		.forEach(num01 -> { 
			amount.put(num01, 0);
			lottos.stream()
						.flatMap(lottoStr -> lottoStr.stream())
						.forEach(num02 -> {
							if(num01 == num02) {
								amount.put(num01, amount.get(num01)+1);
							}
						});
		});
		
	}
	
	private void sortNumbers (Map<Integer,Integer> amount) {
		numbers.clear();
		numbers = amount.entrySet().stream()
				.sorted((o1,  o2) -> o1.getKey() - o2.getKey())
				.sorted((Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) -> o2.getValue() - o1.getValue())
				.collect(Collectors.toList());
	}
	
}
