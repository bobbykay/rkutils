package rkutils.repository;

import java.util.List;

public interface GeneriskQueryDao {

	List getAlleRader(String sqlStatement, int antallKolonner);

}