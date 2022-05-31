import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './barcode.reducer';

export const BarcodeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const barcodeEntity = useAppSelector(state => state.barcode.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="barcodeDetailsHeading">Barcode</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{barcodeEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{barcodeEntity.code}</dd>
          <dt>Product</dt>
          <dd>{barcodeEntity.product ? barcodeEntity.product.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/barcode" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/barcode/${barcodeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BarcodeDetail;
