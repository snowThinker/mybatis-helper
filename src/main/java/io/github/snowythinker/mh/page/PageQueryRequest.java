package io.github.snowythinker.mh.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import io.github.snowthinker.model.PojoHelper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageQueryRequest {
	
	@NotNull(message="当前页数不能为空")
	@Range(min=1, message="请输入合法的当前页数")
	@ApiModelProperty(value="当前页数", required=true, example="1")
	private Integer currentPage;
	
	@NotNull(message="页数大小不能为空")
	@Range(min=1, max=500, message="请输入合法的页数大小")
	@ApiModelProperty(value="页数大小", required=true, example="10")
	private Integer pageSize;
	
	@ApiModelProperty(value="表单条件", required=false)
	private Map<String, Object> conditions = new HashMap<>();
	
	@ApiModelProperty(value="表单排序", required=false)
	private List<PageQuerySort> sorts = new ArrayList<>();
	
	@ApiModelProperty(value="总记录数", hidden=true)
	private Long totalCount;
	
	public Map<String, Object> asMap() {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("pageSize", pageSize);
		rs.put("currentPage", currentPage);
		
		rs.putAll(this.conditions);
		rs.put("sorts", hump2UnderLine(sorts));
		
		return rs;
	}
	
	private List<PageQuerySort> hump2UnderLine(List<PageQuerySort> sortList) {
		for(PageQuerySort pageSort : sortList) {
			String newKey = PojoHelper.hump2Line(pageSort.getOrderBy());
			pageSort.setOrderBy(newKey);
		}
		return sortList;
	}
}
