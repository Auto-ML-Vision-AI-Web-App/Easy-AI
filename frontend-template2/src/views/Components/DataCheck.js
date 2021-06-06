import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';


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
  }
}));

export default function DataCheck() {
  const classes = useStyles();
  const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

  return (
        <>
          <Grid container>
            <Grid item xs={12}>
                    <Paper className={fixedHeightPaper}>
                        <h4>데이터 전처리하기</h4>
                    </Paper>
            </Grid>
          </Grid>
        </>
  );
}