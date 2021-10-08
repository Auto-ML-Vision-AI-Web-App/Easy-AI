import React, { useState } from 'react';
import { Link, withRouter, useHistory } from "react-router-dom";
import axios from 'axios';
import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import LinearProgress from '@material-ui/core/LinearProgress';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Slide from '@material-ui/core/Slide';

import BuildIcon from '@material-ui/icons/Build';

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  stepButton: {
    border: "red",
    backgroundColor: "#eee6c4",
    color: "black",
    fontSize: 30,
    margin: '10px',
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

function AIGenerate(props) {
  const history = useHistory();
  const classes = useStyles();
  const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);
  const [loadingStatus, setLoadingStatus] = React.useState(false)
  const [prevBtnDisabled, setPrevBtnDisabled] = useState(false)
  const [nextBtnDisabled, setNextBtnDisabled] = useState(false)
  const [dataValue, setDataValue] = useState(0)

  const [open, setOpen] = React.useState(false);
  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  function aiMaking(card_value) {
    setLoadingStatus(true);
    console.log("AI model making start");
    const api = axios.create({
      baseURL: 'http://168.188.125.50:20017'
    })
    api.post('/ai-making', {
      params: {
        modelName: card_value
      }
    }).then(function (response) {
      console.log(response.data);
      setLoadingStatus(false);
      history.push({
        pathname: '/admin/ai-checking',
        state: {
          result_model: card_value,
          result_message: response.data
        }
      })
    }).catch(function (error) {
      console.log(error);
    });
  }
  return (
    <>
      <Grid container spacing={3}>
        <Grid item xs={12}>
          <Paper className={fixedHeightPaper}>
            <h2><strong>생성 예정 AI 확인하기</strong></h2>
            <div>
              {props.AIType == "" ?
                <div>
                  <h3>현재 AI가 선택되지 않았습니다.</h3>
                  <h4>AI 선택하기를 눌러 생성할 AI를 선택해주세요.</h4>
                </div>
                :
                <div>
                  <h4>생성할 AI 종류 : {props.AIType}</h4>
                  <p>데이터 수 : {dataValue}</p>
                  <Button
                    //onClick={() => { aiMaking(props.AIType) }}
                    onClick={handleClickOpen}
                    variant="contained"
                    color="primary"
                    size="large"
                    className={classes.button}
                    startIcon={<BuildIcon />}
                  >지금 바로 생성하기</Button>
                  {loadingStatus ?
                    <LinearProgress id="loadingProgress" style={{ display: 'block' }} />
                    : <LinearProgress id="loadingProgress" style={{ display: 'none' }} />}
                </div>
              }</div>
          </Paper>
          <center>
            <Button component={Link} to="/admin/data-checking"
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

      <Dialog
        open={open}
        TransitionComponent={Transition}
        keepMounted
        onClose={handleClose}
        aria-labelledby="alert-dialog-slide-title"
        aria-describedby="alert-dialog-slide-description"
      >
        <DialogTitle id="alert-dialog-slide-title">{"정말 AI를 생성하시겠습니까?"}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-slide-description">
            업로드한 데이터, 입력한 클래스를 기반으로 AI 생성과정을 시작할 것입니다.<br></br>
            시작하기 전에 다시 한번 'AI 생성 관련 정보들'을 확인해주세요.
            //시간 걸릴거라고 추가
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => { aiMaking(props.AIType) }} color="primary">
            생성하기
          </Button>
          <Button onClick={handleClose} color="secondary">
            취소
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}

export default withRouter(AIGenerate);
