package test.lucene.wrapper;

import java.util.*;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

/**
 * This class wrap the Sort class of Lucene so that we can
 * add SortField(s) and keep them append new SortField easily
 * until we really need the Sort
 *
 */
public class WrappedSort {
	private Sort _sort; // Sort
	private List<SortField> _sortFields = new LinkedList<SortField>(); // SortField(s)
	/**
	 * Constructor that only create the instance of Sort
	 */
	public WrappedSort () {
		createSort();
	}
	/**
	 * Constructor that create the instance of Sort and add one SortField
	 * @param sortField The SortField to add
	 */
	public WrappedSort (SortField sortField) {
		createSort().addSortField(sortField);
	}
	/**
	 * Constructor that create the instance of Sort and add a SortField List
	 * @param sortFields The List of SortField to add
	 */
	public WrappedSort (List<SortField> sortFields) {
		createSort().addSortFields(sortFields);
	}
	/**
	 * Create/renew the instance of Sort
	 * @return Self instance so we can do something else directly.
	 */
	public WrappedSort createSort () {
		_sort = new Sort();
		return this;
	}
	/**
	 * Renew the instance of the List of SortField
	 * @return Self instance so we can do something else directly.
	 */
	public WrappedSort createSortField () {
		_sortFields = new LinkedList<SortField>();
		return this;
	}
	/**
	 * Add one SortField
	 * @param sortField The SortField to add
	 * @return Self instance so we can do something else directly.
	 */
	public WrappedSort addSortField (SortField sortField) {
		_sortFields.add(sortField);
		return this;
	}
	/**
	 * Add a List of SortField
	 * @param sortFields The List of SortField to add
	 * @return Self instance so we can do something else directly.
	 */
	public WrappedSort addSortFields (List<SortField> sortFields) {
		_sortFields.addAll(sortFields);
		return this;
	}
	/**
	 * Get the List of SortField
	 * @return The List of SortField
	 */
	public List<SortField> getSortFields () {
		return _sortFields;
	}
	/**
	 * Set SortField(s) to Sort and return the instance of Sort.
	 * @return The instance of Sort
	 */
	public Sort getNativeSort () {
		if (_sortFields.size() == 1)
			_sort.setSort(_sortFields.get(0));
		else
			_sort.setSort((SortField[])_sortFields.toArray());
		return _sort;
	}
}