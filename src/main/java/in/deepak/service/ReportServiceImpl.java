package in.deepak.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.deepak.entity.EligibilityDtlsEntity;
import in.deepak.repository.EligDtlsRepository;
import in.deepak.request.SearchRequest;
import in.deepak.response.SearchResponse;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private EligDtlsRepository repository;

	@Override
	public List<String> getPlanNames() {
		return repository.getUniquePlanNames();
	}

	@Override
	public List<String> getPlanStatuses() {
		return repository.getUniquePlanStatuses();
	}

	@Override
	public List<SearchResponse> searchPlans(SearchRequest request) {

		List<EligibilityDtlsEntity> eligRecords = null;

		if(isSearchReqEmpty(request)) {
			eligRecords = repository.findAll();
		}else {
			String planName = request.getPlanName();
			String planStatus = request.getPlanStatus();
			LocalDate startDate = request.getStartDate();
			LocalDate endDate = request.getEndDate();

			EligibilityDtlsEntity entity = new EligibilityDtlsEntity();

			if(planName!=null && !planName.equals("")) {
				entity.setPlanName(planName);
			}
			if(planStatus!=null && !planStatus.equals("")) {
				entity.setPlanStatus(planStatus);
			}
			if(startDate!=null && endDate!=null) {
				entity.setStartDate(startDate);
				entity.setEndDate(endDate);
			}
			Example<EligibilityDtlsEntity> of = Example.of(entity);
			eligRecords = repository.findAll(of);
		}
		
		List<SearchResponse> response = new ArrayList<>();
		for(EligibilityDtlsEntity eligRecord : eligRecords) {
			SearchResponse sr = new SearchResponse();
			BeanUtils.copyProperties(eligRecord, sr);
			response.add(sr);
		}
		return response;
	}
	
	private boolean isSearchReqEmpty(SearchRequest request) {
		if(request.getPlanName() != null && !request.getPlanName().equals("")) {
			return false;
		}
		if(request.getPlanStatus() != null && !request.getPlanStatus().equals("")) {
			return false;
		}
		if(request.getStartDate() != null && !request.getStartDate().equals("")) {
			return false;
		}
		if(request.getEndDate() != null && !request.getEndDate().equals("")) {
			return false;
		}
		return true;
	}
}