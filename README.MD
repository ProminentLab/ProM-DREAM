# ProM Plugin: Decay Replay Mining to Predict Next Process Events
This ProM plugin implements the Decay Replay Mining (DREAM) preprocessing approach described in [Decay Replay Mining to Predict Next Process Events](https://ieeexplore.ieee.org/document/8811455) by Julian Theis and Houshang Darabi. The plugin considers a Petri net process model as PNML and an CSV formatted event log as input and produces timed state samples that can be used for further machine learning and data science tasks. The plugin enhances and parametrizes each place of the process model with a time decay function. Afterwards, the event log is replayed on the enhanced model and timed state samples are extracted at every discrete timestep observed in the log.

Plugin Documentation: [https://prominentlab.github.io/ProM-DREAM/](https://prominentlab.github.io/ProM-DREAM/)  
Paper: [https://ieeexplore.ieee.org/document/8811455](https://ieeexplore.ieee.org/document/8811455)  
DREAM-NAP main repository: [https://github.com/ProminentLab/DREAM-NAP](https://github.com/ProminentLab/DREAM-NAP)  

# Citation
```
@article{theis2019decay,
  title={Decay Replay Mining to Predict Next Process Events},
  author={Theis, Julian and Darabi, Houshang},
  journal={IEEE Access},
  volume={7},
  pages={119787--119803},
  year={2019},
  publisher={IEEE}
}
```
