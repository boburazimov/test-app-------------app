import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBrand } from 'app/shared/model/brand.model';
import { getEntities as getBrands } from 'app/entities/brand/brand.reducer';
import { IAttachment } from 'app/shared/model/attachment.model';
import { getEntities as getAttachments } from 'app/entities/attachment/attachment.reducer';
import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { IGroup } from 'app/shared/model/group.model';
import { getEntities as getGroups } from 'app/entities/group/group.reducer';
import { IUnit } from 'app/shared/model/unit.model';
import { getEntities as getUnits } from 'app/entities/unit/unit.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { GeneralStatusEnum } from 'app/shared/model/enumerations/general-status-enum.model';
import { getEntity, updateEntity, createEntity, reset } from './product.reducer';

export const ProductUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const brands = useAppSelector(state => state.brand.entities);
  const attachments = useAppSelector(state => state.attachment.entities);
  const categories = useAppSelector(state => state.category.entities);
  const groups = useAppSelector(state => state.group.entities);
  const units = useAppSelector(state => state.unit.entities);
  const productEntity = useAppSelector(state => state.product.entity);
  const loading = useAppSelector(state => state.product.loading);
  const updating = useAppSelector(state => state.product.updating);
  const updateSuccess = useAppSelector(state => state.product.updateSuccess);
  const generalStatusEnumValues = Object.keys(GeneralStatusEnum);
  const handleClose = () => {
    props.history.push('/product');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBrands({}));
    dispatch(getAttachments({}));
    dispatch(getCategories({}));
    dispatch(getGroups({}));
    dispatch(getUnits({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...productEntity,
      ...values,
      brand: brands.find(it => it.id.toString() === values.brand.toString()),
      photo: attachments.find(it => it.id.toString() === values.photo.toString()),
      category: categories.find(it => it.id.toString() === values.category.toString()),
      group: groups.find(it => it.id.toString() === values.group.toString()),
      unit: units.find(it => it.id.toString() === values.unit.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          status: 'ACTIVE',
          ...productEntity,
          brand: productEntity?.brand?.id,
          photo: productEntity?.photo?.id,
          category: productEntity?.category?.id,
          group: productEntity?.group?.id,
          unit: productEntity?.unit?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testApp.product.home.createOrEditLabel" data-cy="ProductCreateUpdateHeading">
            Create or edit a Product
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="product-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="product-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 40, message: 'This field cannot be longer than 40 characters.' },
                }}
              />
              <ValidatedField
                label="Description"
                id="product-description"
                name="description"
                data-cy="description"
                type="text"
                validate={{
                  maxLength: { value: 200, message: 'This field cannot be longer than 200 characters.' },
                }}
              />
              <ValidatedField label="Vendor Code" id="product-vendorCode" name="vendorCode" data-cy="vendorCode" type="text" />
              <UncontrolledTooltip target="vendorCodeLabel">VendorCode - Article</UncontrolledTooltip>
              <ValidatedField
                label="Code"
                id="product-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 10, message: 'This field cannot be longer than 10 characters.' },
                }}
              />
              <ValidatedField label="Top" id="product-top" name="top" data-cy="top" check type="checkbox" />
              <ValidatedField label="Vat Rate" id="product-vatRate" name="vatRate" data-cy="vatRate" type="text" />
              <UncontrolledTooltip target="vatRateLabel">vatRate - ставка НДС</UncontrolledTooltip>
              <ValidatedField
                label="Made In"
                id="product-madeIn"
                name="madeIn"
                data-cy="madeIn"
                type="text"
                validate={{
                  maxLength: { value: 30, message: 'This field cannot be longer than 30 characters.' },
                }}
              />
              <ValidatedField
                label="Info"
                id="product-info"
                name="info"
                data-cy="info"
                type="text"
                validate={{
                  maxLength: { value: 200, message: 'This field cannot be longer than 200 characters.' },
                }}
              />
              <ValidatedField label="Status" id="product-status" name="status" data-cy="status" type="select">
                {generalStatusEnumValues.map(generalStatusEnum => (
                  <option value={generalStatusEnum} key={generalStatusEnum}>
                    {generalStatusEnum}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="product-brand" name="brand" data-cy="brand" label="Brand" type="select">
                <option value="" key="0" />
                {brands
                  ? brands.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="product-photo" name="photo" data-cy="photo" label="Photo" type="select">
                <option value="" key="0" />
                {attachments
                  ? attachments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="product-category" name="category" data-cy="category" label="Category" type="select">
                <option value="" key="0" />
                {categories
                  ? categories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="product-group" name="group" data-cy="group" label="Group" type="select">
                <option value="" key="0" />
                {groups
                  ? groups.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="product-unit" name="unit" data-cy="unit" label="Unit" type="select">
                <option value="" key="0" />
                {units
                  ? units.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/product" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ProductUpdate;
