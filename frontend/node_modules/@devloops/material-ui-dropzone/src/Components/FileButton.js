import React, { useState, useEffect, useRef } from "react";
import PropTypes from "prop-types";
import { FileButtonStyles } from "../utils/styles";
import { makeStyles, Icon, Fab } from "@material-ui/core";
const FıleButton = ({
  onChange,
  onError,
  inputProps = {},
  fabText = "",
  fabIcon = "cloud_upload",
  fabProps = {},
  ButtonComponent = Fab
}) => {
  const inputRef = useRef(null);
  const [state, stateUpdate] = useState({});
  const classes = makeStyles(FileButtonStyles)();
  const setState = newStateObject => stateUpdate({ ...state, ...newStateObject });
  useEffect(() => {}, []);
  return (
    <>
      <ButtonComponent
        variant={fabText.length > 0 ? "extended" : "round"}
        aria-label="delete"
        className={classes.fab}
        {...fabProps}
        onClick={() => inputRef.current.click()}
      >
        <Icon className={fabText.length > 0 ? classes.extendedIcon : ""}>{fabIcon}</Icon>
        {fabText}
      </ButtonComponent>
      <input
        type="file"
        accept="image/*,.pdf"
        ref={inputRef}
        hidden
        {...inputProps}
        onChange={onChange}
        onError={onError}
      />
    </>
  );
};

FıleButton.propTypes = {
  ButtonComponent: PropTypes.oneOfType([PropTypes.element, PropTypes.func, PropTypes.elementType]),
  fabIcon: PropTypes.string,
  fabProps: PropTypes.object,
  onChange: PropTypes.func,
  onError: PropTypes.func,
  fabText: PropTypes.string,
  inputProps: PropTypes.object
};

export default FıleButton;
