import React, {useState} from 'react';
import { Link } from "react-router-dom";

import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';

import Button from '@material-ui/core/Button';

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  stepButton: {
    border: "red",
    backgroundColor: "#eee6c4",
    color: "black",
    fontSize: 30,
    margin : '10px',
    "&:hover,&:focus": {
      backgroundColor: "#333333",
      color: "#fff",
      boxShadow:
        "0 14px 26px -12px rgba(51, 51, 51, 0.42), 0 4px 23px 0px rgba(0, 0, 0, 0.12), 0 8px 10px -5px rgba(51, 51, 51, 0.2)",
    },
  },
  root: {
    display: 'flex',
  },
  container: {
    paddingTop: theme.spacing(4),
    paddingBottom: theme.spacing(4),
  },
  paper: {
    padding: theme.spacing(2),
    display: 'flex',
    overflow: 'auto',
    flexDirection: 'column',
  },
  fixedHeight: {
    height: 240,
},
}));

export default function DataCheck(props) {
  const classes = useStyles();
  const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);
  const [prevBtnDisabled, setPrevBtnDisabled] = useState(true)
  const [nextBtnDisabled, setNextBtnDisabled] = useState(true)
  
  return (
        <>
          <Grid container spacing={3}>
                <Grid item xs={12}>
                    <Paper className={fixedHeightPaper}>
                      <h2><strong>데이터 확인하기</strong></h2>
                      <div>
                        {props.location.state==undefined ?
                        <div>
                        <h3>현재 데이터가 업로드 되지 않았습니다.</h3>
                        <h4>데이터 업로드하기를 눌러 데이터를 입력해주세요.</h4>
                        </div>
                        :
                        <div>
                        <h4>{props.location.state.project_id}</h4>
                        <Link>{props.location.state.data_result}</Link>
                        </div>
                        }</div>
                    </Paper>
                    <center>
                    <Button component={Link} to="/admin/data-uploading"
                    disabled={prevBtnDisabled}
                    className={classes.stepButton}>
                    이전
                    </Button>
                    <Button component={Link} to="/admin/ai-making"
                    disabled={nextBtnDisabled}
                    className={classes.stepButton}>
                    다음
                    </Button>
                    </center>
                </Grid>
            </Grid>
        </>
  );
}