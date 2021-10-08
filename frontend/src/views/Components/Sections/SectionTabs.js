import React from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";

// @material-ui/icons
import Face from "@material-ui/icons/Face";
import GitHubIcon from '@material-ui/icons/GitHub';
import BookIcon from '@material-ui/icons/Book';
// core components
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import CustomTabs from "components/CustomTabs/CustomTabs.js";

import styles from "assets/jss/material-kit-react/views/componentsSections/tabsStyle.js";

const useStyles = makeStyles(styles);

export default function SectionTabs() {
  const classes = useStyles();
  return (
    <div className={classes.section}>
      <div className={classes.container}>
        <div id="nav-tabs">
          <h3>Easy AI란 무엇인가요?</h3>
          <GridContainer>
            <GridItem xs={12} sm={12} md={12}>
              <h3>
                <small>우리 Easy AI는요...</small>
              </h3>
              <CustomTabs
                headerColor="info"
                tabs={[
                  {
                    tabName: "Easy AI",
                    tabIcon: Face,
                    tabContent: (
                      <p className={classes.textCenter}>
                        Easy AI 설명을 넣는다넣는다넣는다
                      </p>
                    ),
                  },
                  {
                    tabName: "Github",
                    tabIcon: GitHubIcon,
                    tabContent: (
                      <p className={classes.textCenter}>
                        https://github.com/Auto-ML-Vision-AI-Web-App
                      </p>
                    ),
                  },
                  {
                    tabName: "Blog",
                    tabIcon: BookIcon,
                    tabContent: (
                      <p className={classes.textCenter}>
                        <h1>블로그를 넣는다넣는다</h1>
                      </p>
                    ),
                  },
                ]}
              />
            </GridItem>
          </GridContainer>
        </div>
      </div>
    </div>
  );
}
