import React, {useState} from 'react';
import { Link } from "react-router-dom";
import axios from 'axios';
import { makeStyles } from '@material-ui/core/styles';

export default function SampleDataCheck(props) {
  return (
        <>
          <h1>{props.location.state.dataNums}</h1>
          <h1>{props.location.state.className}</h1>
        </>
  );
}
