import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product.reducer';

export const ProductDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productEntity = useAppSelector(state => state.product.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productDetailsHeading">Product</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{productEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{productEntity.description}</dd>
          <dt>
            <span id="vendorCode">Vendor Code</span>
            <UncontrolledTooltip target="vendorCode">VendorCode - Article</UncontrolledTooltip>
          </dt>
          <dd>{productEntity.vendorCode}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{productEntity.code}</dd>
          <dt>
            <span id="top">Top</span>
          </dt>
          <dd>{productEntity.top ? 'true' : 'false'}</dd>
          <dt>
            <span id="vatRate">Vat Rate</span>
            <UncontrolledTooltip target="vatRate">vatRate - ставка НДС</UncontrolledTooltip>
          </dt>
          <dd>{productEntity.vatRate}</dd>
          <dt>
            <span id="madeIn">Made In</span>
          </dt>
          <dd>{productEntity.madeIn}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{productEntity.info}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{productEntity.status}</dd>
          <dt>Brand</dt>
          <dd>{productEntity.brand ? productEntity.brand.id : ''}</dd>
          <dt>Photo</dt>
          <dd>{productEntity.photo ? productEntity.photo.id : ''}</dd>
          <dt>Category</dt>
          <dd>{productEntity.category ? productEntity.category.id : ''}</dd>
          <dt>Group</dt>
          <dd>{productEntity.group ? productEntity.group.id : ''}</dd>
          <dt>Unit</dt>
          <dd>{productEntity.unit ? productEntity.unit.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/product" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product/${productEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductDetail;
