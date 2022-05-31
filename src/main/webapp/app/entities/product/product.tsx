import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProduct } from 'app/shared/model/product.model';
import { getEntities, reset } from './product.reducer';

export const Product = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const productList = useAppSelector(state => state.product.entities);
  const loading = useAppSelector(state => state.product.loading);
  const totalItems = useAppSelector(state => state.product.totalItems);
  const links = useAppSelector(state => state.product.links);
  const entity = useAppSelector(state => state.product.entity);
  const updateSuccess = useAppSelector(state => state.product.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="product-heading" data-cy="ProductHeading">
        Products
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/product/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Product
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={productList ? productList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {productList && productList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('name')}>
                    Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    Description <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('vendorCode')}>
                    Vendor Code <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('code')}>
                    Code <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('top')}>
                    Top <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('vatRate')}>
                    Vat Rate <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('madeIn')}>
                    Made In <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('info')}>
                    Info <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('status')}>
                    Status <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Brand <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Photo <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Category <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Group <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Unit <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {productList.map((product, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/product/${product.id}`} color="link" size="sm">
                        {product.id}
                      </Button>
                    </td>
                    <td>{product.name}</td>
                    <td>{product.description}</td>
                    <td>{product.vendorCode}</td>
                    <td>{product.code}</td>
                    <td>{product.top ? 'true' : 'false'}</td>
                    <td>{product.vatRate}</td>
                    <td>{product.madeIn}</td>
                    <td>{product.info}</td>
                    <td>{product.status}</td>
                    <td>{product.brand ? <Link to={`/brand/${product.brand.id}`}>{product.brand.id}</Link> : ''}</td>
                    <td>{product.photo ? <Link to={`/attachment/${product.photo.id}`}>{product.photo.id}</Link> : ''}</td>
                    <td>{product.category ? <Link to={`/category/${product.category.id}`}>{product.category.id}</Link> : ''}</td>
                    <td>{product.group ? <Link to={`/group/${product.group.id}`}>{product.group.id}</Link> : ''}</td>
                    <td>{product.unit ? <Link to={`/unit/${product.unit.id}`}>{product.unit.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/product/${product.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`/product/${product.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`/product/${product.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">No Products found</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Product;
