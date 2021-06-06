import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";

const FıleInput = ({}) => {
  const [state, stateUpdate] = useState({});

  const setState = newStateObject => stateUpdate({ ...state, ...newStateObject });
  useEffect(() => {}, []);
  return (
    <>
      <Fab variant="extended" aria-label="delete" className={classes.fab}>
        <NavigationIcon className={classes.extendedIcon} />
        Extended
      </Fab>
    </>
  );
};
FıleInput.propTypes = {
  name: PropTypes.string
};
export default FıleInput;
