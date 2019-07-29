# DREAM: Decay Replay Mining

## About
This ProM plugin implements the Decay Replay Mining (DREAM) preprocessing approach described in [Decay Replay Mining to Predict Next Events](https://arxiv.org/abs/1903.05084) by Julian Theis and Houshang Darabi. The plugin considers a Petri net process model as PNML and an CSV formatted event log as input and produces timed state samples that can be used for further machine learning and data science tasks. The plugin enhances and parametrizes each place of the process model with a time decay function. Afterwards, the event log is replayed on the enhanced model and timed state samples are extracted at every discrete timestep observed in the log.


## References
If you are using this plugin, please cite:
> Theis, Julian, and Houshang Darabi. "DREAM-NAP: Decay Replay Mining to Predict Next Process Activities." *arXiv preprint arXiv:1903.05084* (2019).

BibTeX:
```
@article{theis2019dream,
  title={DREAM-NAP: Decay Replay Mining to Predict Next Process Activities},
  author={Theis, Julian and Darabi, Houshang},
  journal={arXiv preprint arXiv:1903.05084},
  year={2019}
}
```

## Implementation
The DREAM approach is implemented as a [ProM 6](http://www.promtools.org/) plugin. Please download the following JAR containing the plugin:

[ProM-DREAM-20190712.jar](http://prominentlab.github.io/ProM-DREAM/DIST/ProM-DREAM-20190712.jar)

Please create a folder *plugins* in your ProM installation directory and place the plugin JAR in it. In order to be loaded correctly, please start ProM in the following way (Windows):

```
java -da -Xmx4G -classpath "./ProM68_dist/*;./ProM68_lib/*;./plugins/*" -Djava.library.path=.//ProM68_lib -Djava.util.Arrays.useLegacyMergeSort=true org.processmining.contexts.uitopia.UI
```


## Usage

### Decay Function Enhancement
1. Load the Petri net that you want to enhance with Time Decay Functions as a PNML together with the required CSV event log into your ProM workspace.
2. Select the *Decay Replay Mining (DREAM) - Decay Function Enhancement* plugin, use the two loaded files from your workspace as input, and click *Start*.
3. Parse the CSV event log by following the prompt.
4. Configure the conversion from CSV to XES. Select the Case and Event column and map the Timestamp column to the Completion Time.
5. Select the type of Decay Function (default: Linear)
6. The plugin creates an object Decay Functions in your workspace that shows the learned decay functions for each place of your Petri net. These functions can be (optionally) stored to disk.

### Replay and Extract Timed State Samples
1. Load the Petri net as a PNML together with the corresponding Time Decay Functions and a CSV event log into your ProM workspace.
2. Select the *Decay Replay Mining (DREAM) - Replay and Extract Timed State Samples* plugin, use the three loaded files from your workspace as input, and click *Start*.
3. Parse the CSV event log by following the prompt.
4. Configure the conversion from CSV to XES. Select the Case and Event column and map the Timestamp column to the Completion Time.
5. The plugin creates an object Timed State Samples in your workspace that shows the extracted timed state samples from initializing your trace to the last event of that trace. These samples can be (optionally) stored to disk as a CSV file.

## Contact
This plugin has been developed by the [PROMINENT](http://prominent.uic.edu) research group of the University of Illinois at Chicago, in particular by Julian Theis, Sourabh Parime, and Prof. Houshang Darabi.

The source code is maintained by Julian Theis ([jtheis3@uic.edu](jtheis3@uic.edu)). The corresponding author is Prof. Houshang Darabi ([hdarabi@uic.edu](hdarabi@uic.edu)).
