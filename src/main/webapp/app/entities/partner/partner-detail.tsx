import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './partner.reducer';

export const PartnerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const partnerEntity = useAppSelector(state => state.partner.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="partnerDetailsHeading">Partner</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{partnerEntity.id}</dd>
          <dt>
            <span id="phonenumber">Phonenumber</span>
          </dt>
          <dd>{partnerEntity.phonenumber}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{partnerEntity.code}</dd>
          <dt>
            <span id="firstName">First Name</span>
          </dt>
          <dd>{partnerEntity.firstName}</dd>
          <dt>
            <span id="lastName">Last Name</span>
          </dt>
          <dd>{partnerEntity.lastName}</dd>
          <dt>
            <span id="gender">Gender</span>
          </dt>
          <dd>{partnerEntity.gender}</dd>
          <dt>
            <span id="age">Age</span>
          </dt>
          <dd>{partnerEntity.age}</dd>
          <dt>
            <span id="isBlocked">Is Blocked</span>
          </dt>
          <dd>{partnerEntity.isBlocked ? 'true' : 'false'}</dd>
          <dt>
            <span id="partnerType">Partner Type</span>
          </dt>
          <dd>{partnerEntity.partnerType}</dd>
          <dt>
            <span id="inn">Inn</span>
          </dt>
          <dd>{partnerEntity.inn}</dd>
          <dt>
            <span id="pinfl">Pinfl</span>
          </dt>
          <dd>{partnerEntity.pinfl}</dd>
          <dt>Group</dt>
          <dd>{partnerEntity.group ? partnerEntity.group.id : ''}</dd>
          <dt>Social</dt>
          <dd>{partnerEntity.social ? partnerEntity.social.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/partner" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/partner/${partnerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PartnerDetail;
