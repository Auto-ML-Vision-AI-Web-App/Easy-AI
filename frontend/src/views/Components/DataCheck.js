import React, {useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';

import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import SkipNextIcon from '@material-ui/icons/SkipNext';
import SkipPreviousIcon from '@material-ui/icons/SkipPrevious';

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
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