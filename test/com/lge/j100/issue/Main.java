package com.lge.j100.issue;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.simpleflatmapper.csv.CsvParser;

public class Main {

	private static final String URL = "data/Consumer_Complaints.csv";

	@Disabled
	@Test
	public void quiz3() throws URISyntaxException, IOException {
		// [① Top10 회사이름]과 그리고 [② Top10제품이름]에 모두 해당하는 레코드에 대하여,
		// 회사 별 제품 개수를 구하여라.
		// 최종 결과 Type: Map<String, Map<String, Long>> map;
		
		List<String> cLst = getTopTen(Complain::getCompany);
		List<String> pLst = getTopTen(Complain::getProduct);
		
		Predicate<Complain> predicate = (t) -> cLst.contains(t.getCompany())
				&& pLst.contains(t.getProduct());
		
		Map<String, Map<String, Long>> map 
			= newComplainStream().filter(predicate)
				.collect(Collectors.groupingBy(Complain::getCompany, 
						Collectors.groupingBy(Complain::getProduct,
							Collectors.counting())));
	}

	List<String> getTopTen(Function<Complain, String> f) throws IOException {
		return newComplainStream()
				.collect(Collectors.groupingBy(f, Collectors.counting()))
				.entrySet().stream()
				.sorted(Comparator.comparing(Entry<String, Long>::getValue)
						.reversed())
				.limit(10).map(e -> e.getKey()).collect(Collectors.toList());
	}

	@Disabled
	@Test
	public void quiz2() throws URISyntaxException, IOException {

		try {
			ExecutorService exec = Executors.newSingleThreadScheduledExecutor();
			Future<List<String>> future = exec
					.submit(() -> getTopTen(Complain::getProduct));

			List<String> byCompany = future.get();
			System.out.println(byCompany.size());
			terminateThreads(exec);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Disabled
	@Test
	public void quiz1() throws URISyntaxException, IOException {
		ExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		Future<List<String>> future = exec
				.submit(() -> getTopTen(Complain::getCompany));

		List<String> byCompany;
		try {
			byCompany = future.get();
			System.out.println(byCompany.size());
			terminateThreads(exec);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void terminateThreads(ExecutorService executor)
			throws InterruptedException {
		if (executor.awaitTermination(3, TimeUnit.SECONDS)) {
			System.out.println("task completed");
		} else {
			System.out.println("Forcing shutdown...");
			executor.shutdownNow();
		}
	}

	Stream<Complain> newComplainStream() throws IOException {
		return CsvParser.skip(1).stream(Files.newBufferedReader(Paths.get(URL)))
				.map(array -> new Complain(Arrays.asList(array))).parallel();
	}

	List<Complain> loadDataset(BufferedReader in) throws IOException {
		CsvReader csv = new CsvReader(URL);
		if (csv.readHeaders()) {
			// List<String> colNames = new
			// LinkedList<String>(Arrays.asList(csv.getHeaders()));
		}

		List<Complain> complains = new LinkedList<>();
		while (csv.readRecord()) {
			int card = csv.getColumnCount();
			String[] r = csv.getValues();
			if (card == 18) {
				Complain fields = new Complain(Arrays.asList(r));
				complains.add(fields);
			} else {
				System.out.println("[ERROR]" + card + ":" + Arrays.toString(r));
			}
		}

		return complains;
	}
}

class Complain {
	private LocalDate dataReceived;
	private String product;
	private String subproduct;
	private String issue;
	private String subissue;
	private String consumerComplaintNarrative;
	private String companyPublicResponse;
	private String company;
	private String state;
	private String zipCode;
	private String tags;
	private String consumerConsentProvided;
	private String submittedVia;
	private LocalDate dateSentToCompany;
	private String companyResponseToConsumer;
	private boolean timelyResponse;
	private boolean consumerDisputed;
	private String complaintId;

	final static DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("MM/d/yyyy");

	public Complain(List<String> next) {
		int index = 0;

		this.dataReceived = LocalDate.parse(next.get(index++), formatter);
		this.product = next.get(index++);
		this.subproduct = next.get(index++);
		this.issue = next.get(index++);
		this.subissue = next.get(index++);
		this.consumerComplaintNarrative = next.get(index++);
		this.companyPublicResponse = next.get(index++);
		this.company = next.get(index++).trim();
		this.state = next.get(index++);
		this.zipCode = next.get(index++);
		this.tags = next.get(index++);
		this.consumerConsentProvided = next.get(index++);
		this.submittedVia = next.get(index++);
		this.dateSentToCompany = LocalDate.parse(next.get(index++), formatter);
		this.companyResponseToConsumer = next.get(index++);
		this.timelyResponse = "Yes".equals(next.get(index++));
		this.consumerDisputed = "Yes".equals(next.get(index++));
		this.complaintId = next.get(index++);
	}

	public LocalDate getDataReceived() {
		return dataReceived;
	}

	public String getProduct() {
		return product;
	}

	public String getSubproduct() {
		return subproduct;
	}

	public String getIssue() {
		return issue;
	}

	public String getSubissue() {
		return subissue;
	}

	public String getConsumerComplaintNarrative() {
		return consumerComplaintNarrative;
	}

	public String getCompanyPublicResponse() {
		return companyPublicResponse;
	}

	public String getCompany() {
		return company;
	}

	public String getState() {
		return state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getTags() {
		return tags;
	}

	public String getConsumerConsentProvided() {
		return consumerConsentProvided;
	}

	public String getSubmittedVia() {
		return submittedVia;
	}

	public LocalDate getDateSentToCompany() {
		return dateSentToCompany;
	}

	public String getCompanyResponseToConsumer() {
		return companyResponseToConsumer;
	}

	public boolean isTimelyResponse() {
		return timelyResponse;
	}

	public boolean isConsumerDisputed() {
		return consumerDisputed;
	}

	public String getComplaintId() {
		return complaintId;
	}

	public static DateTimeFormatter getFormatter() {
		return formatter;
	}

	@Override
	public String toString() {
		return "Complain [dataReceived=" + dataReceived + ", product=" + product
				+ ", subproduct=" + subproduct + ", issue=" + issue
				+ ", subissue=" + subissue + ", consumerComplaintNarrative="
				+ consumerComplaintNarrative + ", companyPublicResponse="
				+ companyPublicResponse + ", company=" + company + ", state="
				+ state + ", zipCode=" + zipCode + ", tags=" + tags
				+ ", consumerConsentProvided=" + consumerConsentProvided
				+ ", submittedVia=" + submittedVia + ", dateSentToCompany="
				+ dateSentToCompany + ", companyResponseToConsumer="
				+ companyResponseToConsumer + ", timelyResponse="
				+ timelyResponse + ", consumerDisputed=" + consumerDisputed
				+ ", complaintId=" + complaintId + "]";
	}

}
