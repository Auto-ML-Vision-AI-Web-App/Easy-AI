import React, { useState, Component, Fragment } from 'react';
import { Switch, Route, Link, withRouter, useHistory } from "react-router-dom";

import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';

import Grid from '@material-ui/core/Grid';
import Button from "components/CustomButtons/Button.js";
import Paper from '@material-ui/core/Paper';

import Highcharts from "highcharts";
import PieChart from "highcharts-react-official";

import CustomListDown from "components/CustomDropdown/CustomListDown.js";

export default withRouter(DataCheck);

const useStyles = makeStyles((theme) => ({
    paper: {
        padding: theme.spacing(2),
        display: 'flex',
        overflow: 'auto',
        flexDirection: 'column',
    },
    fixedHeight: {
        height: 500,
    },
}));

function DataCheck({ location }) {
    const classes = useStyles();
    const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);
    const [dataset, setDataset] = useState(location.state.dataset);

    return (
        <>
            <Grid item xs={12}>
                {console.log(dataset)}
                <DataResultChart dataset={dataset}></DataResultChart>
                <hr style={{ background: 'gray' }}></hr>
                
                <Grid container spacing={3}>
                <Grid item xs={12}>
                  <Paper>
                  <center>
                        <CustomListDown dataset={dataset}></CustomListDown>
                    </center>
                  </Paper>
                </Grid>
                </Grid>
            </Grid>
            <Grid item xs={12}>
                <center>
                    <Link to="/admin/ai-making/">
                        <Button style={{ color: 'white', backgroundColor: '#6F3637' }}>업로드한 데이터로 AI 생성하러 가기</Button>
                    </Link>
                </center>
            </Grid>
        </>
    );
}

/*React Chart*/
class DataResultChart extends Component {

    render() {
      const jsonfile = this.props.dataset;
      let total = 0;
      jsonfile.map((data, idx) => (
        total = total + data.successSize
      ));
  
      const options = {
        chart: {
          plotBackgroundColor: null,
          plotBorderWidth: null,
          plotShadow: false,
          type: 'pie'
        },
        title: {
          text: '데이터 업로드 결과'
        },
        tooltip: {
          pointFormat: '{series.name}: <b>{point.size}개</b>'
        },
        plotOptions: {
          pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
              enabled: true,
              format: '<b>{point.name}</b> : {point.percentage:.1f} %'
            }
          }
        },
  
        series: [{
          name: '데이터 수',
          colorByPoint: true,
          data: [{
            name: jsonfile[0].className,
            size: jsonfile[0].successSize,
            y: jsonfile[0].successSize / total * 100,
            color: '#08AAC1',
            sliced: true,
            selected: true
          }, {
            name: jsonfile[1].className,
            size: jsonfile[1].successSize,
            y: jsonfile[1].successSize / total * 100,
            color: '#5C5C5C'
          }]
        }],
  
        responsive: {
          rules: [{
            condition: {
              maxWidth: 300
            },
            chartOptions: {
              legend: {
                layout: 'horizontal',
                align: 'center',
                verticalAlign: 'bottom'
              }
            }
          }]
        }
  
      }
      return (
        <Fragment>
          <PieChart highcharts={Highcharts} options={options} />
        </Fragment>
      );
    }
  }