package in.deepak.service;

import java.util.List;

import in.deepak.request.SearchRequest;
import in.deepak.response.SearchResponse;

public interface ReportService {

	public List<String> getPlanNames();

	public List<String> getPlanStatuses();

	public List<SearchResponse> searchPlans(SearchRequest request);
}
