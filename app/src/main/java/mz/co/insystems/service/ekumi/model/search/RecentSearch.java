package mz.co.insystems.service.ekumi.model.search;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import mz.co.insystems.service.ekumi.base.AbstractBaseVO;
import mz.co.insystems.service.ekumi.model.user.User;

@DatabaseTable(tableName = RecentSearch.TABLE_NAME, daoClass = RecentSearchDaoImpl.class)
public class RecentSearch extends AbstractBaseVO {

    public static final String TABLE_NAME			    = "recent_search";
    public static final String COLUMN_ID 			    = "id";
    public static final String COLUMN_QUERY_STRING		= "query";
    public static final String COLUMN_DATE		        = "date";
    public static final String COLUMN_USER		        = "user_id";
    private static final long serialVersionUID = 1802045685732135746L;

    @DatabaseField(columnName = COLUMN_ID, id = true, generatedId = true)
    protected long id;
    @DatabaseField(columnName = COLUMN_QUERY_STRING)
    protected String queryString;
    @DatabaseField
    protected Date date;
    @DatabaseField(columnName = COLUMN_USER, foreign = true, foreignAutoRefresh = true)
    protected User user;

    public RecentSearch() {
    }

    public RecentSearch(long id) {
        this.id = id;
    }

    public RecentSearch(String query, Date date, User currentUser) {
        this.queryString = query;
        this.date = date;
        this.user = currentUser;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
