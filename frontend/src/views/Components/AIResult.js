import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';

import { Link } from "react-router-dom";

import IconButton from '@material-ui/core/IconButton';
import SkipNextIcon from '@material-ui/icons/SkipNext';
import SkipPreviousIcon from '@material-ui/icons/SkipPrevious';


const useStyles = makeStyles((theme) => ({
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
                                    <h4>{props.location.state.result_model}</h4>
                                    <Link>{props.location.state.model_url}</Link>
                                </div>
                            }</div>
                    </Paper>
                    <center>
                    <IconButton disabled={prevBtnDisabled} color="secondary" aria-label="go to next step">
                    <SkipPreviousIcon style={{ fontSize: 80 }}/>
                    </IconButton>
                    
                    <IconButton disabled={nextBtnDisabled} color="secondary" aria-label="go to next step">
                    <SkipNextIcon style={{ fontSize: 80 }} />
                    </IconButton>
                    </center>
                </Grid>
            </Grid>
        </>
    );
}