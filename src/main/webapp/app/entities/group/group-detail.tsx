import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './group.reducer';

export const GroupDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const groupEntity = useAppSelector(state => state.group.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="groupDetailsHeading">Group</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{groupEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{groupEntity.name}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{groupEntity.info}</dd>
          <dt>
            <span id="catalog">Catalog</span>
          </dt>
          <dd>{groupEntity.catalog}</dd>
          <dt>Parent</dt>
          <dd>{groupEntity.parent ? groupEntity.parent.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/group" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/group/${groupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GroupDetail;
