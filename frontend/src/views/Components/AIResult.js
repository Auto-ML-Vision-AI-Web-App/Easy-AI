import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';

import { Link } from "react-router-dom";


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

export default function AIResult(props) {
    const [isSetAI, setAI] = useState();
    const [prevBtnDisabled, setPrevBtnDisabled] = useState(true)
    const [nextBtnDisabled, setNextBtnDisabled] = useState(true)

    const classes = useStyles();
    const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);
    //const getParams = this.props.location.state.result_model;
    return (
        <>
            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <Paper className={fixedHeightPaper}>
                        <h2><strong>AI 결과 확인하기</strong></h2>
                        <div>
                            {props.location.state == undefined ?
                                <div>
                                    <h3>현재 AI를 생성하지 않았습니다.</h3>
                                    <h4>AI 생성하기를 눌러 AI를 생성해주세요.</h4>
                                </div>
                                :
                                <div>
                                    <h4>AI 종류 : {props.location.state.result_model}</h4>
                                    <Link style={{color:"gray"}}>AI 링크 : {props.location.state.model_url}</Link>
                                </div>
                            }</div>
                    </Paper>
                    <center>
                    <Button component={Link} to="/admin/ai-making"
                    disabled={prevBtnDisabled}
                    className={classes.stepButton}>
                    이전
                    </Button>
                    <Button component={Link} to="/admin/dashboard"
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